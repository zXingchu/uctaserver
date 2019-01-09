package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.EntryApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryApplicationRepo extends JpaRepository<EntryApplication, String> {
}
