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

    private String nickName;

    private int gender;

    private String avatarUrl;

    private String city = "Nanjing";

    private String province = "Jiangsu";

    private int likeNum = 0;

    private int treadNum = 0;

    private String labels = "";
}
