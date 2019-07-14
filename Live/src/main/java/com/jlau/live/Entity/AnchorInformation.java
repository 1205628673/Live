package com.jlau.live.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Entity
@Table(name = "anchor_information")
public class AnchorInformation implements Serializable{
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int anchorId;
    @Column
    private String anchorSex;
    @Column
    private String anchorAlias;
    @Column
    private String anchorSignal;
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

    public String getAnchorSignal() {
        return anchorSignal;
    }

    public void setAnchorSignal(String anchorSignal) {
        this.anchorSignal = anchorSignal;
    }

    public String getAnchorAlias() {
        return anchorAlias;
    }

    public void setAnchorAlias(String anchorAlias) {
        this.anchorAlias = anchorAlias;
    }

    public String getAnchorSex() {
        return anchorSex;
    }

    public void setAnchorSex(String anchorSex) {
        this.anchorSex = anchorSex;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }
}
