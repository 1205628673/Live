package com.jlau.live.controller;

import com.jlau.live.Entity.AnchorAccount;
import com.jlau.live.Entity.AnchorInformation;
import com.jlau.live.response.Response;
import com.jlau.live.service.AnchorAccountService;
import com.jlau.live.service.AnchorInfomationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@RestController
public class AnchorController {
    @Autowired
    AnchorAccountService anchorAccountService;
    @Autowired
    AnchorInfomationService anchorInfomationService;
    @PostMapping(value = "/anchorLogin")
    public ResponseEntity<Response> checkAnchor(@RequestParam("username")String username,@RequestParam("password")String password){
        Response response = anchorAccountService.findAnchor(username,password);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/anchor/{id}")
    public ResponseEntity<Response> getAnchor(Integer id){
        Response response = anchorInfomationService.getAnchorInformation(id);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @PutMapping(value = "/anchor")
    public ResponseEntity<Response> saveAnchor(AnchorAccount anchorAccount){
        Response response = anchorAccountService.save(anchorAccount);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @PutMapping(value = "/anchorinfo")
    public ResponseEntity<Response> saveAnchorInfo(AnchorInformation anchorInformation){
        Response response = anchorInfomationService.save(anchorInformation);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}
