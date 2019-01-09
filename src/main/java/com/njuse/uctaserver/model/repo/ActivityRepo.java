package com.njuse.uctaserver.model.repo;

import com.njuse.uctaserver.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, String> {

}
