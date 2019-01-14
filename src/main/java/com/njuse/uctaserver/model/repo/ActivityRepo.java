package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, String> {
    
    List<Activity> findAllByOwnerId(String ownId);

    List<Activity> findAllByAuditStatus(String auditStatus);

    List<Activity> findAllByNameContaining(String name);

}
