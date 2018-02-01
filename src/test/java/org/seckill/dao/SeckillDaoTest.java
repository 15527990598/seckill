package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)// 加载spring容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})// 告诉junit spring的配置文件
public class SeckillDaoTest {

    // 注入Dao实现依赖
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void testQueryById() throws Exception {

        Seckill seckill = seckillDao.queryById(1);
        System.out.println(seckill.getName());
        System.out.println(seckill);

    }

    @Test
    public void testQueryAll() throws Exception {

        List<Seckill> seckills = seckillDao.queryAll(0,1);
        for (Seckill seckill : seckills){
            System.out.println(seckill);

        }
    }

    @Test
    public void testReduceNumber() throws Exception {

        Date date = new Date();
        System.out.println(date);
        int num = seckillDao.reduceNumber(2,date);
        System.out.println("updateCount="+num);
    }
}