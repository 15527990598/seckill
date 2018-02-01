package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)// 加载spring容器
@ContextConfiguration({"classpath:spring/spring-dao.xml"})// 告诉junit spring的配置文件
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {

        long userPhone = 15527990598L;
        int insertCount = 0;
        //try {
            insertCount = successKilledDao.insertSuccessKilled(1,userPhone);
        //}catch(Exception e){
            //e.printStackTrace();
        //}
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long userPhone = 15527990598L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1,15527990598L);
        System.out.println(successKilled);
    }
}