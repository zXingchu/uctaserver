package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, String> {
    
    @Query(value = "select new com.njuse.uctaserver.dto.ApplicationDTO" +
            "(aly.id, aly.time, aly.actId, a.name, aly.userId, aly.status, '') " +
            "from com.njuse.uctaserver.model.entity.Application aly " +
            "join com.njuse.uctaserver.model.entity.Activity a on a.id=aly.actId " +
            "where a.ownerId=?1")
    List<ApplicationDTO> getAllByUserId(String userId);

    boolean existsByActIdAndUserId(String actId, String userId);

}
