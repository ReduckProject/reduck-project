package net.reduck.jpa.test.entity;

import lombok.Data;
import net.reduck.jpa.processor.PropertyEncryption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Reduck
 * @since 2022/8/6 22:40
 */
@Table(name = "user")
@Entity
@Data
public class User extends BaseEntity {

    @PropertyEncryption
    private String username;

    @PropertyEncryption
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonalInfo info;
}
