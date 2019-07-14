package com.jlau.live.service;

import com.jlau.live.Entity.UserInformation;
import com.jlau.live.repository.UserInformationRepository;
import com.jlau.live.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Optional;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserInformationService {
    Log log = LogFactory.getLog(UserInformationService.class);
    @Autowired
    UserInformationRepository userInformationRepository;
    public Response<UserInformation> getUserInformationById(Integer id){
        try {
            Optional<UserInformation> optional = userInformationRepository.findOne(new Specification<UserInformation>() {
                @Override
                public Predicate toPredicate(Root<UserInformation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate p = criteriaBuilder.equal(root.get("user_id"), id);
                    criteriaQuery.where(p);
                    return criteriaQuery.getRestriction();
                }
            });
            if (optional.isPresent()) {
                return new Response<>("true", "ok", optional.get());
            } else {
                return new Response<>("false", "页面走丢了", null);
            }
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false", "页面走丢了", null);
        }
    }
    public Response<String> save(UserInformation userInformation){
        try{
            userInformation.setOperateTime(new Date());
            userInformationRepository.save(userInformation);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","更新出错","");
        }
        return new Response<>("true","更新成功","");
    }

}
