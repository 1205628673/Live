package com.jlau.live.service;

import com.jlau.live.Entity.AnchorAccount;
import com.jlau.live.repository.AnchorAccountRepository;
import com.jlau.live.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Transactional
@Service
public class AnchorAccountService {
    Log log = LogFactory.getLog(AnchorAccount.class);
    @Autowired
    AnchorAccountRepository anchroAccountRepository;
    public Response<? extends Object> findAnchor(String username,String userpasswd){
        try {
            Optional<AnchorAccount> one = anchroAccountRepository.findOne(new Specification<AnchorAccount>() {
                @Override
                public Predicate toPredicate(Root<AnchorAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate p1 = criteriaBuilder.equal(root.get("username"), "username");
                    Predicate p2 = criteriaBuilder.equal(root.get("userpasswd"), "userpasswd");
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(p1);
                    predicates.add(p2);
                    return criteriaBuilder.and(p1, p2);
                }
            });
            if (one.isPresent()) {
                return new Response<Integer>("true", "ok", one.get().getAnchorId());
            } else {
                return new Response<String>("false", "用户名或密码错误", null);
            }
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<Integer>("false","登录错误",null);
        }
    }
    @Modifying
    public Response<? extends Object> save(AnchorAccount anchorAccount){
        try {
            anchorAccount.setOperateTime(new Date());
            Response ancRsp = findAnchor(anchorAccount.getUsername(),anchorAccount.getPasssword());
            if(ancRsp.getSuccess().equals("false")){
                anchorAccount.setRegistrationTime(new Date());
            }else{
                //能进入该块的Response必定能查到用户
                anchorAccount.setRegistrationTime(((AnchorAccount)ancRsp.getData()).getRegistrationTime());
            }
            anchroAccountRepository.save(anchorAccount);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","保存失败",null);
        }
        return new Response<>("true","保存成功",null);
    }
    public Response<? extends Object> deleteById(Integer id){
        try {
            anchroAccountRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","删除失败",null);
        }
        return new Response<>("true","删除成功",null);
    }

}
