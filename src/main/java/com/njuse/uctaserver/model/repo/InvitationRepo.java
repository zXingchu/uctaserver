package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepo  extends JpaRepository<Invitation, String> {
}
