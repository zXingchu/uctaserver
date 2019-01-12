package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepo  extends JpaRepository<Invitation, String> {

    List<Invitation> findAllByActId(String actId);

    List<Invitation> findAllByUserId(String userId);

}
