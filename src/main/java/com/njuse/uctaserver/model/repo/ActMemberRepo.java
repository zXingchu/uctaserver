package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.ActivityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActMemberRepo extends JpaRepository<ActivityMember, Integer> {

    ActivityMember findFirstByActIdAndUserId(String actId, String userId);


//    @Query("select new com.njuse.uctaserver.model.entity.User" +
//            "(u.id, u.name, u.age, u.like_num, u.tread_num, u.labels) " +
//            "from user u " +
//            "join activity_member a on u.id=a.user_id " +
//            "where a.act_id=?1")
//    List<User> getUsersPartInAct(String actId);
//
//    @Query("select new com.njuse.uctaserver.model.entity.Activity" +
//            "(act.id, act.create_time, act.start_time, act.end_time, act.name, act.description, " +
//            "act.place, act.number, act.part_number, act.owner_id, act.audit_status, act.status) " +
//            "from activity act " +
//            "join activity_member a on act.id=a.act_id " +
//            "where a.user_id=?1")
//    List<Activity> getAllActByUserId(String userId);

}
