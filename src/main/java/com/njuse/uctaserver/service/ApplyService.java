package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.EntryApplication;
import org.springframework.http.HttpStatus;
import java.util.List;

public interface ApplyService {

    HttpStatus add(EntryApplication entryApplication);

    HttpStatus delete(String id);

    EntryApplication get(String id);

    List<EntryApplication> getAllByUserId(String userId);

    List<EntryApplication> getAllByActivity(String actId);

    HttpStatus isPermit(String id, int resCode);
}
