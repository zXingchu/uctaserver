package com.njuse.uctaserver.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.njuse.uctaserver.until.ApplyStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "application")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class EntryApplication implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 28)
    private String id;

    private String actId;

    private String userId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date time = new Date();

    private String description;

    private String status = ApplyStatus.APPLY.getName();

}
