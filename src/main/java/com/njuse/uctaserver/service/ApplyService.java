package com.njuse.uctaserver.service;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Application;
import org.springframework.http.HttpStatus;
import java.util.List;

public interface ApplyService {

    HttpStatus add(Application application);

    HttpStatus delete(String id);

    Application get(String id);

    List<ApplicationDTO> getAllByUserId(String userId);

    List<ApplicationDTO> getAllByActivity(String actId);

    HttpStatus isPermit(String id, int resCode);
}
