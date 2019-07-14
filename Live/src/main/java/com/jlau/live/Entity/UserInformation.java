package com.jlau.live.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Entity
@Table(name = "user_information")
public class UserInformation implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String userAlias;
    @Column
    private int userId;
    @Column
    private String userSex;
    @Column
    private String userSignal;
    @Column
    private Date operateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getUserSignal() {
        return userSignal;
    }

    public void setUserSignal(String userSignal) {
        this.userSignal = userSignal;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}
