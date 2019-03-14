package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.UserService;
import com.njuse.uctaserver.until.MemberAttitude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User get(String id) {
        if (!userRepo.existsById(id)) {
            return null;
        }
        return userRepo.getOne(id);
    }

    @Override
    public HttpStatus addOrUpdate(User user) {
        if (userRepo.existsById(user.getId())) {
            userRepo.save(user);
            return HttpStatus.OK;
        }
        userRepo.save(user);
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus likeOrThread(String id, int maCode) {
        if (!userRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        User user = userRepo.getOne(id);
        if (maCode == MemberAttitude.LIKE.getIndex())
            user.setLikeNum(user.getLikeNum() + 1);
        else
            user.setTreadNum(user.getTreadNum() + 1);
        userRepo.save(user);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus addLabel(String id, String label) {
        if (!userRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        User user = userRepo.getOne(id);
        String labels = user.getLabels();
        if (labels.equals("")) {
            labels = label + ";1";
        } else {
            labels = "";
            String[] labelsTemp = labels.split(";");
            String[] labelsStr = labelsTemp[0].split(" ");
            String[] labelsNum = labelsTemp[1].split(" ");

            int i;
            for (i = 0; i < labelsStr.length; i++) {
                if (labelsStr[i].equals(label)) {
                    labelsNum[i] = String.valueOf(Integer.valueOf(labelsNum[i]) + 1);
                    swapLabels(labelsStr, labelsNum, i);
                    break;
                }
            }
            if (i < labelsStr.length) {
                label = "";
            }
            labels = StringUtils.join(labelsStr, " ");
            labels = labels + label + ";";
            labels = labels + StringUtils.join(labelsNum, " ");
            if (i == labelsStr.length) {
                labels = labels + " 1";
            }
        }
        user.setLabels(labels);
        userRepo.save(user);
        return HttpStatus.OK;
    }

    private void swapLabels(String[] labelsStr, String[] labelsNum, int index) {
        int indexNum = Integer.parseInt(labelsNum[index]);
        for (int i = 0; i < labelsNum.length; i++) {
            int num = Integer.parseInt(labelsNum[i]);
            if (num < indexNum) {
                String temp = labelsStr[index];
                labelsStr[index] = labelsStr[i];
                labelsStr[i] = temp;
                temp = labelsNum[index];
                labelsNum[index] = labelsNum[i];
                labelsNum[i] = temp;
            }
        }
    }
}
