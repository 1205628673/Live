package com.jlau.live.repository;

import com.jlau.live.Entity.UserAccount;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
public interface UserAccountReposotory extends JpaRepository<UserAccount,String>,JpaSpecificationExecutor<UserAccount> {

}
