package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, String>, JpaSpecificationExecutor<Activity> {
    
    List<Activity> findAllByOwnerIdOrderByStartTimeDesc(String ownId);

    List<Activity> findAllByAuditStatus(String auditStatus);

    List<Activity> findAllByAuditStatusOrderByStartTimeDesc(String auditStatus);

    List<Activity> findAllByNameContainingOrderByStartTimeDesc(String name);

    boolean existsByIdAndOwnerId(String actId, String ownerId);

    

}
