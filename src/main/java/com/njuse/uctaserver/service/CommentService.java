package com.njuse.uctaserver.service;

import com.njuse.uctaserver.dto.OrgCommentDTO;
import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.model.entity.PlaceComment;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CommentService {

    List<OrgCommentDTO> getAllByActId(String actId);

    List<PlaceComment> getAllByPlace(String place);

    HttpStatus addCommentOnOrg(OrgComment orgComment);

    HttpStatus addCommentOnPlace(PlaceComment placeComment);

    double getScoreByActId(String actId);

    double getScoreByPlace(String place);

}
