package com.njuse.uctaserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "org_comment")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class OrgComment implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private String description;

    private double score;

    private String actId;

    private String userId;

    private Date createTime = new Date();

}
