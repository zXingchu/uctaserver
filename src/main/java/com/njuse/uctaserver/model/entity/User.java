package com.njuse.uctaserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class User implements Serializable {

    @Id
    private String id;

    private int likeNum;

    private int treadNum;

    private String labels;
}
