package com.jlau.live.controller;

import com.jlau.live.Entity.LiveRoom;
import com.jlau.live.response.Response;
import com.jlau.live.service.LiveRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@RestController
public class LiveRoomController {
    @Autowired
    LiveRoomService liveRoomService;
    @GetMapping(value = "/liveroom/{page}")
    public ResponseEntity<Response> getLiveRooms(@PathVariable Integer page){
        Response response = liveRoomService.getRoomByPage(page,15);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/liveroom/{name}/{page}")
    public ResponseEntity<Response> getLiveRoomByName(@PathVariable String name,@PathVariable Integer page){
        Response response = liveRoomService.getRoomByName(name,page,15);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @PutMapping(value = "/liveroom")
    public ResponseEntity<Response> saveLiveRoom(LiveRoom liveRoom){
        Response response = liveRoomService.save(liveRoom);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

}
