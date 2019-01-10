package com.njuse.uctaserver.service.impl;


import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.model.entity.PlaceComment;
import com.njuse.uctaserver.model.repo.OrgCommentRepo;
import com.njuse.uctaserver.model.repo.PlaceCommentRepo;
import com.njuse.uctaserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    OrgCommentRepo orgCommentRepo;

    @Autowired
    PlaceCommentRepo placeCommentRepo;

    @Override
    public List<OrgComment> getAllByActId(String actId) {
        List<OrgComment> orgComments = orgCommentRepo.findAllByActId(actId);
        return orgComments.isEmpty() ? null : orgComments;
    }

    @Override
    public List<PlaceComment> getAllByPlace(String place) {
        List<PlaceComment> placeComments = placeCommentRepo.findByPlace(place);
        return placeComments.isEmpty() ? null : placeComments;
    }

    @Override
    public HttpStatus addCommentOnOrg(OrgComment orgComment) {
        orgCommentRepo.save(orgComment);
        String id = orgComment.getId();
        if (id != null) {
            return HttpStatus.CREATED;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @Override
    public HttpStatus addCommentOnPlace(PlaceComment placeComment) {
        placeCommentRepo.save(placeComment);
        String id = placeComment.getId();
        if (id != null) {
            return HttpStatus.CREATED;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @Override
    public int getScoreByActId(String actId) {
        return 0;
    }

    @Override
    public int getScoreByPlace(String place) {
        return 0;
    }
}
