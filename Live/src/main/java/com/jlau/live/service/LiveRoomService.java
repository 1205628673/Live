package com.jlau.live.service;

import com.jlau.live.Entity.LiveRoom;
import com.jlau.live.repository.LiveRoomRepository;
import com.jlau.live.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Service
@Transactional
public class LiveRoomService {
    @Autowired
    LiveRoomRepository liveRoomRepository;
    Log log = LogFactory.getLog(LiveRoomService.class);
    public Response<Page<LiveRoom>> getRoomByPage(int page,int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page liveRoomPage = liveRoomRepository.findAll(new Specification<LiveRoom>() {
                @Override
                public Predicate toPredicate(Root<LiveRoom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Order order = criteriaBuilder.desc(root.get("operate_time"));
                    return criteriaQuery.orderBy(order).getRestriction();
                }
            }, pageable);
            return new Response<>("true","ok",liveRoomPage);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","页面走丢",null);
        }
    }
    public Response<Page<LiveRoom>> getRoomByName(String name,int page,int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page liveRoomPage = liveRoomRepository.findAll(new Specification<LiveRoom>() {
                @Override
                public Predicate toPredicate(Root<LiveRoom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.like(root.get("roomName"), "%"+name+"%");
                }
            }, pageable);
            return new Response<>("true", "ok", liveRoomPage);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","页面走丢",null);
        }
    }
    public Response<String> save(LiveRoom room){
        try {
            liveRoomRepository.save(room);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","更新错误","");
        }
        return new Response<>("true","ok","");
    }
    public Response<String> deleteRoom(int id){
        try {
            liveRoomRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getCause());
            return new Response<>("false","删除错误","");
        }
        return new Response<>("true","删除成功","");
    }
}
