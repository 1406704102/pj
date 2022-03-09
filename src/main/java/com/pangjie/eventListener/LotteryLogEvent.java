package com.pangjie.eventListener;

import org.springframework.context.ApplicationEvent;

public class LotteryLogEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public LotteryLogEvent(Object source) {
        super(source);
    }

    public LotteryLogEvent(Object source, int userId, int goodsId) {
        super(source);
        this.userId = userId;
        this.goodsId = goodsId;
    }

    private int userId;
    private int goodsId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
