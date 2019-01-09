package com.njuse.uctaserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "user")
public class User implements Serializable {

    @Id
    private String id;

    private int likeNum;

    private int treadNum;

    private String labels;


    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getTreadNum() {
        return treadNum;
    }

    public void setTreadNum(int treadNum) {
        this.treadNum = treadNum;
    }
}
