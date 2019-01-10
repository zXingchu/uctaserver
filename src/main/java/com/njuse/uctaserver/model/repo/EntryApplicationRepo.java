package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.EntryApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryApplicationRepo extends JpaRepository<EntryApplication, String> {

    List<EntryApplication> findAllByUserId(String userId);

    List<EntryApplication> findAllByActId(String actId);

}
