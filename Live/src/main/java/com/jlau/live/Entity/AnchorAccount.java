package com.jlau.live.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Entity
@Table(name = "anchor_account")
public class AnchorAccount implements Serializable{
    @Id
    @GeneratedValue
    private int anchorId;
    @Column
    private Date operateTime;
    @Column
    private Date registrationTime;
    @Column
    private int status;
    @Column
    private String username;
    @Column
    private String passsword;

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
}
