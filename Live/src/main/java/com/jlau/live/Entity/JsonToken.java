package com.jlau.live.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/7/7.
 */
public class JsonToken implements Serializable{
    private Integer userId;
    private String token;
    private Date operateTime;
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
