package net.reduck.jpa.test.entity;

import lombok.Data;
import net.reduck.apt.annotation.Crypto;
import net.reduck.apt.annotation.CryptoConverter;
import net.reduck.apt.annotation.Test;
import net.reduck.jpa.entity.convert.PropertyEncryptionConvert;
import net.reduck.jpa.processor.PropertyEncryption;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Reduck
 * @since 2022/9/21 17:04
 */
@Entity
@Table(name = "house")
@Data
public class House extends BaseEntity {

    @PropertyEncryption
    @CryptoConverter(convert = Test.class)
    private String name;


    @Convert(converter = PropertyEncryptionConvert.class)
    private String address;

    @Crypto
    private String master;

}
