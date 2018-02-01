package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)// 加载spring容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})// 告诉junit spring的配置文件
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;


    @Test
    public void testSeckill() throws Exception {
        long id = 1;
        // get and put
        Seckill seckill = redisDao.getSeckill(id);
        if(seckill==null){
            // redis中对象为空，则去数据库获取
            seckill = seckillDao.queryById(id);
            if(seckill!=null){
                // 将数据库获取到的对象传递到redis
                String result = redisDao.putSeckill(seckill);
                System.out.println("result="+result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }else{
            System.out.println("redis中的seckill="+seckill);
        }
    }
}