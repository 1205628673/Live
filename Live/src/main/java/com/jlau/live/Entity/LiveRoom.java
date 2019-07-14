package com.jlau.live.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Entity
@Table(name = "live_room")
public class LiveRoom implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    @Column
    private int anchorId;
    @Column
    private String roomName;
    @Column
    private Date operateTime;

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public int getRoomId() {
            return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRommId() {
        return roomId;
    }

    public void setRommId(int rommId) {
        this.roomId = rommId;
    }

    public int getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(int anchorId) {
        this.anchorId = anchorId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
