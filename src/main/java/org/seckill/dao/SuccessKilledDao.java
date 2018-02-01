package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by 浴缸 on 2017/12/26.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId 商品id
     * @param userPhone 用户手机号
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId")long seckillId ,@Param("userPhone")long userPhone);

    /**
     * 根据id查询并携带秒杀产品对象实体
     * @param seckillId 商品id
     * @param userPhone 用户手机号
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
 }
