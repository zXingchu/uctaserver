package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.OrgComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgCommentRepo extends JpaRepository<OrgComment, String> {
}
