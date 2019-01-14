package com.njuse.uctaserver.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.njuse.uctaserver.until.InvitationStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "invitation")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Invitation {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private String actId;

    private String userId;

    private String inviterId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date time = new Date();



    private String status = InvitationStatus.INVITE.getName();

}
