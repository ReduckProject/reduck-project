package net.reduck.jpa.test.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Reduck
 * @since 2022/8/10 15:06
 */
@Table(name = "personal_info")
@Entity
@Data
public class PersonalInfo extends BaseEntity {

    private String phoneNo;

    private String email;
}
