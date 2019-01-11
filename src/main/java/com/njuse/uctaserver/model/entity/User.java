package com.njuse.uctaserver.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class User implements Serializable {

    @Id
    private String id;

    private String name;

    private int age;

    private int likeNum;

    private int treadNum;

    private String labels;
}
