package com.jlau.live.repository;

import com.jlau.live.Entity.AnchorAccount;
import com.jlau.live.service.AnchorAccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Repository
public interface AnchorAccountRepository extends JpaRepository<AnchorAccount,Integer>,JpaSpecificationExecutor<AnchorAccount>{

}
