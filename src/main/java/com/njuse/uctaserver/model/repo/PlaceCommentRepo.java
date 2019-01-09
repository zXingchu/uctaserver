package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.PlaceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceCommentRepo extends JpaRepository<PlaceComment, String> {
}
