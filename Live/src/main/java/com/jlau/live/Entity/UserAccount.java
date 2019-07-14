package com.jlau.live.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable{
    @Column
    @NotNull
    @Size(min = 6,max = 15)
    private String username;
    @Column
    @NotNull
    @Size(min = 6,max = 30)
    private String password;
    @Column
    private Date registrationTime;
    @Column
    private Date operateTime;
    @Id
    @GeneratedValue
    private int userId;
    @Column
    private int status;
    interface Login{}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
