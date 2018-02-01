package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis数据访问对象
 * Created by Yuk on 2017/12/29.
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao (String ip,int port){
        this.jedisPool = new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    // 获取redis中的对象
    public Seckill getSeckill(long seckillId){
        // redis操作逻辑
        // get -> byte[] -> 反序列化 -> Object[Seckill]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:"+seckillId;
                // 采用自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                // 获取到缓存
                if(bytes!=null){
                    // 先创建一个空对象
                    Seckill seckill = schema.newMessage();
                    // 赋值，seckill被反序列化
                    ProtobufIOUtil.mergeFrom(bytes,seckill,schema);

                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    // 往redis传递对象
    public String putSeckill(Seckill seckill){
        // set -> Object[Seckill] -> 序列化 -> byte[] -> redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:"+seckill.getSeckillId();
                // 对象，schema，缓存器
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存1小时
                int timeout =  60 * 60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
