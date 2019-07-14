package com.jlau.live.service;

import com.jlau.live.Entity.AnchorInformation;
import com.jlau.live.repository.AnchorInformationRepository;
import com.jlau.live.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Optional;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AnchorInfomationService {
    @Autowired
    AnchorInformationRepository anchorInformationRepository;
    Log log = LogFactory.getLog(AnchorInfomationService.class);
    public Response<AnchorInformation> getAnchorInformation(Integer id){
        try {
            Optional<AnchorInformation> optional = anchorInformationRepository.findOne(new Specification<AnchorInformation>() {
                @Override
                public Predicate toPredicate(Root<AnchorInformation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate p = criteriaBuilder.equal(root.get("anchor_id"), id);
                    criteriaQuery.where(p);
                    return criteriaQuery.getRestriction();
                }
            });
            if (optional.isPresent()) {
                return new Response<>("true", "ok", optional.get());
            } else {
                return new Response<>("false", "页面走丢", null);
            }
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","页面走丢",null);
        }
    }

    public Response<String> save(AnchorInformation anchorInformation){
        try{
            anchorInformationRepository.save(anchorInformation);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","更新失败","");
        }
        return new Response<>("true","更新成功","");
    }
    public Response<Page<AnchorInformation>> getAnchorInfos(int page,int size){
        try{
            Pageable pageable = PageRequest.of(page,size);
            Page<AnchorInformation> anchorInformationPage = anchorInformationRepository.findAll(new Specification<AnchorInformation>() {
                @Override
                public Predicate toPredicate(Root<AnchorInformation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Order order = criteriaBuilder.desc(root.get(""));
                    criteriaQuery.orderBy(order);
                    return criteriaQuery.getRestriction();
                }
            },pageable);
            return new Response<>("true","ok",anchorInformationPage);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","发生错误",null);
        }
    }
}
