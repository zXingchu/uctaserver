package com.njuse.uctaserver.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.njuse.uctaserver.until.ActivityStatus;
import com.njuse.uctaserver.until.AuditStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "activity")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Activity implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createTime = new Date();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    private String name;

    private String description;

    private String place;

    private int number;

    private int partNumber;

    private String ownerId;

    private String auditStatus = AuditStatus.AUDIT.getName();

    private String status = ActivityStatus.BEFORE_ACT.getName();

}
