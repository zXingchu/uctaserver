package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.dto.OrgCommentDTO;
import com.njuse.uctaserver.model.entity.OrgComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgCommentRepo extends JpaRepository<OrgComment, String> {

    List<OrgComment> findAllByActId(String actId);

    @Query("select new com.njuse.uctaserver.dto.OrgCommentDTO" +
            "(o.id, o.description, o.score, o.actId, u.avatarUrl, u.nickName, o.userId) " +
            "from com.njuse.uctaserver.model.entity.OrgComment o " +
            "join com.njuse.uctaserver.model.entity.User u on o.userId=u.id " +
            "where o.actId=?1")
    List<OrgCommentDTO> getCommentsByActId(String actId);

}
