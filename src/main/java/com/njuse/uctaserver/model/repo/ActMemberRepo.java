package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActMemberRepo extends JpaRepository<ActivityMember, Integer> {

    ActivityMember findFirstByActIdAndUserId(String actId, String userId);


    @Query("select new com.njuse.uctaserver.model.entity.User" +
            "(u.id, u.name, u.gender, u.avatarUrl, u.city, u.province, u.likeNum, u.treadNum, u.labels) " +
            "from com.njuse.uctaserver.model.entity.User u " +
            "join com.njuse.uctaserver.model.entity.ActivityMember a on u.id=a.userId " +
            "where a.actId=?1")
    List<User> getUsersPartInAct(String actId);
//
    @Query("select new com.njuse.uctaserver.model.entity.Activity" +
            "(act.id, act.createTime, act.startTime, act.endTime, act.name, act.description, " +
            "act.place, act.number, act.partNumber, act.ownerId, act.auditStatus, act.status) " +
            "from com.njuse.uctaserver.model.entity.Activity act " +
            "join com.njuse.uctaserver.model.entity.ActivityMember a on act.id=a.actId " +
            "where a.userId=?1")
    List<Activity> getAllActByUserId(String userId);

}
