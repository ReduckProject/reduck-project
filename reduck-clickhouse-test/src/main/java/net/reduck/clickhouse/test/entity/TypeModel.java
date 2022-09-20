package net.reduck.clickhouse.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Reduck
 * @since 2022/9/17 15:42
 */
@Entity
@Table(name = "type_model")
@Data
public class TypeModel {

    /**
     * 版本
     */
    @Version
    @Column(name = "version")
    private int version;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private String updateTime;

    @Id
    private Long id;

    @Column(name = "phone_no")
    private String[] phoneNo;

    private String name;
}
