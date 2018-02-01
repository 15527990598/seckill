package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)// 加载spring容器
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})// 告诉junit spring的配置文件
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1);
        logger.info("exposer={}",exposer);
        /**
         * Exposer{exposed=true, md5='b88b7f812a3841ea515f51334c8c14ec', seckillId=1, now=0, start=0, end=0}
         */
    }

    @Test
    public void testExcuteSeckill() throws Exception {
        String md5 = "b88b7f812a3841ea515f51334c8c14ec";
        long userPhone = 15527990510L;
        SeckillExecution seckillExecution = seckillService.excuteSeckill(1,userPhone,md5);
        logger.info("seckillExecution={}",seckillExecution);
    }

    @Test
    // 集成测试代码完整逻辑，可重复执行
    public void testSeckillLogic(){
        Exposer exposer = seckillService.exportSeckillUrl(6);
        if (exposer.isExposed()){
            logger.info("exposer={}",exposer);
            String md5 = exposer.getMd5();
            long userPhone = 15527990511L;
            try{
                SeckillExecution seckillExecution = seckillService.excuteSeckill(1,userPhone,md5);
                logger.info("seckillExecution={}",seckillExecution);
            }catch (RepeatKillException e){
                logger.info(e.getMessage());
            }catch (SeckillCloseException e){
                logger.info(e.getMessage());
            }
        }else{
            // 秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }
}