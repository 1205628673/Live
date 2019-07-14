package com.jlau.live.repository;

import com.jlau.live.Entity.AnchorInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by cxr1205628673 on 2019/6/30.
 */
@Repository
public interface AnchorInformationRepository extends JpaRepository<AnchorInformation,Integer>,JpaSpecificationExecutor<AnchorInformation>{
}
