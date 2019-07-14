package com.jlau.live.controller;

import com.jlau.live.Entity.UserAccount;
import com.jlau.live.Entity.UserInformation;
import com.jlau.live.response.Response;
import com.jlau.live.service.UserAccountService;
import com.jlau.live.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.ReactiveScriptExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by cxr1205628673 on 2019/6/28.
 */
@RestController
public class UserController {
    @Autowired
    UserInformationService userInformationService;
    @Autowired
    private StringRedisTemplate redisClient;
    @Autowired
    private UserAccountService userAccountService;
    @PostMapping(value = "/userLogin")
    public ResponseEntity<Response> checkUser(@RequestParam("username")String username,@RequestParam("password")String password){
        Response response = userAccountService.findUser(username,password);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/users/{page}/{size}")
    public ResponseEntity<Response> getUsers(@PathVariable Integer page,@PathVariable Integer size){
        Response<Page> response = userAccountService.findAllUsers(page,size);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @PutMapping(value = "/user")
    public ResponseEntity<Response> saveUser(@Validated UserAccount userAccount){
        Response response = userAccountService.save(userAccount);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/userinfo/{id}")
    public ResponseEntity<Response> getUser(@PathVariable Integer id){
        Response response = userInformationService.getUserInformationById(id);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @PutMapping(value = "/userinfo")
    public ResponseEntity<Response> saveUserInfo(UserInformation userInformation){
        Response response = userInformationService.save(userInformation);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/api/token/{key}")
    public ResponseEntity<Response> checkToken(@PathVariable String key){
        Response response = userAccountService.checkToken(key);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
    @GetMapping(value = "/api/info/{token}")
    public ResponseEntity<Response> getInfoByToken(@PathVariable String token){
        Response response = userAccountService.getIdByToken(token);
        return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}
