package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 封装秒杀执行后结果
 * Created by Yuk on 2017/12/27.
 */
public class SeckillExecution {

    // 秒杀id
    private long seckillId;

    // 状态
    private int state;

    // 秒杀详细信息
    private String stateInfo;

    // 秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStateEnum stateEnum,SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.successKilled = successKilled;
        this.stateInfo = stateEnum.getStateInfo();
        this.state = stateEnum.getState();
    }

    public SeckillExecution(long seckillId,SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.stateInfo = stateEnum.getStateInfo();
        this.state = stateEnum.getState();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
