package com.jlau.live.service;

import com.jlau.live.Entity.UserAccount;
import com.jlau.live.cache.TokenCacheModule;
import com.jlau.live.repository.UserAccountReposotory;
import com.jlau.live.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserAccountService {
    @Autowired
    UserAccountReposotory userAccountReposotory;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TokenCacheModule tokenCacheModule;
    Log log = LogFactory.getLog(UserAccount.class);
    public Response<String> findUser(String username,String password){
        Optional<UserAccount> optional = userAccountReposotory.findOne(new Specification<UserAccount>() {
            @Override
            public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("username"),username));
                predicates.add(criteriaBuilder.equal(root.get("password"),password));
                Predicate[] p = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(p)));
                return criteriaQuery.getRestriction();
            }
        });
        if(optional.isPresent()) {
            return new Response("true","ok",optional.get());
        }else{
            return new Response("false","用户名或密码错误",null);
        }
    }
    public Response<String> checkToken(String key){//实现个人主页根据token进行跳转
        String token = tokenCacheModule.checkToken(key);
        if(token != null){
            return new Response<>("true","ok","");//有缓存
        }else{
            return new Response<>("false","not","");//无缓存，要前端跳登录
        }
    }
    public Response getIdByToken(String token){
        String id = (String)redisTemplate.opsForValue().get(token);
        try {
            System.out.println(1 / 0);
        }catch (Exception e){
            throw e;
        }
        return new Response<>("true",token,redisTemplate.opsForValue().get(token));
    }
    public Response<String> deleteUserById(Integer id) {
        try {
            userAccountReposotory.deleteById(id.toString());
        }catch (Exception e) {
            log.error(e.getCause());
            return new Response<>("false","删除发生错误",null);
        }
        return new Response<>("true","删除成功",null);
    }

    public Response<String> save(UserAccount userAccount){
        try {
            userAccount.setOperateTime(new Date());
            Response userRsp = findUser(userAccount.getUsername(),userAccount.getPassword());
            if(userRsp.getSuccess().equals("false")){
                userAccount.setRegistrationTime(new Date());
            }else{
                //进入该块的Response必定是能查到用户的
                userAccount.setRegistrationTime(((UserAccount)userRsp.getData()).getRegistrationTime());
            }
            userAccountReposotory.save(userAccount);
        } catch (Exception e) {
            log.error(e.getCause());
            return new Response<>("false","修改数据错误",null);
        }
        return new Response<>("true","修改成功",null);
    }

    public Response<Page> findAllUsers(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserAccount> p = userAccountReposotory.findAll(new Specification<UserAccount>() {
            @Override
            public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                 Order order = criteriaBuilder.desc(root.get("operateTime"));
                criteriaQuery.orderBy(order);
                return criteriaQuery.getRestriction();
            }
        }, pageable);
        return new Response<>("true","查询成功",p);
    }

}
