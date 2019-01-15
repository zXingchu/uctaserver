package com.njuse.uctaserver.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.njuse.uctaserver.until.ActivityStatus;
import com.njuse.uctaserver.until.AuditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "activity")
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Activity implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private Date createTime = new Date();

    private Date startTime;

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
