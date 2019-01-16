package com.njuse.uctaserver.service.impl;


import com.njuse.uctaserver.dto.OrgCommentDTO;
import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.model.entity.PlaceComment;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.OrgCommentRepo;
import com.njuse.uctaserver.model.repo.PlaceCommentRepo;
import com.njuse.uctaserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final ActivityRepo activityRepo;

    private final OrgCommentRepo orgCommentRepo;

    private final PlaceCommentRepo placeCommentRepo;

    @Autowired
    public CommentServiceImpl(ActivityRepo activityRepo, OrgCommentRepo orgCommentRepo, PlaceCommentRepo placeCommentRepo) {
        this.activityRepo = activityRepo;
        this.orgCommentRepo = orgCommentRepo;
        this.placeCommentRepo = placeCommentRepo;
    }

    @Override
    public List<OrgCommentDTO> getAllByActId(String actId) {
        List<OrgCommentDTO> orgComments = orgCommentRepo.getCommentsByActId(actId);
        return orgComments.isEmpty() ? Collections.emptyList() : orgComments;
    }

    @Override
    public List<PlaceComment> getAllByPlace(String place) {
        List<PlaceComment> placeComments = placeCommentRepo.findByPlace(place);
        return placeComments.isEmpty() ? Collections.emptyList() : placeComments;
    }

    @Override
    public HttpStatus addCommentOnOrg(OrgComment orgComment) {
        if(activityRepo.existsById(orgComment.getId()))
            return HttpStatus.NOT_FOUND;
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
    public double getScoreByActId(String actId) {
        double score = 0;
        List<OrgComment> orgComments = orgCommentRepo.findAllByActId(actId);
        if (orgComments.isEmpty())
            return -1;
        for (OrgComment orgComment : orgComments) {
            score += orgComment.getScore();
        }
        score = score / orgComments.size();
        return score;
    }

    @Override
    public double getScoreByPlace(String place) {
        double score = 0;
        List<PlaceComment> placeComments = placeCommentRepo.findByPlace(place);
        if (placeComments.isEmpty())
            return -1;
        for (PlaceComment placeComment : placeComments) {
            score += placeComment.getScore();
        }
        score = score / placeComments.size();
        return score;
    }
}
