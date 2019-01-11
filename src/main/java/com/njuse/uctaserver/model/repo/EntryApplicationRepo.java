package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.EntryApplication;
import com.njuse.uctaserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntryApplicationRepo extends JpaRepository<EntryApplication, String> {

    List<EntryApplication> findAllByUserId(String userId);

    List<EntryApplication> findAllByActId(String actId);

}
