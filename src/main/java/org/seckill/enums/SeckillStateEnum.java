package org.seckill.enums;

/**
 * 使用枚举表述常量数据字段
 * Created by Yuk on 2017/12/27.
 */
public enum SeckillStateEnum {
    SUCCESS(0,"秒杀成功"),
    END(1,"秒杀结束"),
    REPEAT_KILL(2,"重复秒杀"),
    INNER_ERROR(3,"系统异常"),
    DATA_REWRITE(4,"数据篡改");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnum stateOf(int index){
        for(SeckillStateEnum state : values()){
            if(state.getState()==index){
                return state;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(SUCCESS.getState());
    }

}
