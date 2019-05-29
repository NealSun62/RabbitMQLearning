package com.sun.overweight.common.jwt;

/**
 * Created by bf on 2018/11/15.
 */
public class AccessToken {
    private Long userId;
    private Long deadTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(Long deadTime) {
        this.deadTime = deadTime;
    }
}
