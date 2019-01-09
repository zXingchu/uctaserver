package com.njuse.uctaserver.model.entity;


import java.io.Serializable;


public class Activity implements Serializable{

    private Long id;

    private String description;

    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
