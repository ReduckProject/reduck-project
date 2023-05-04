package net.reduck.jpa.test.controller.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Gin
 * @since 2022/12/28 15:36
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CasUserInfoResponse {

    private String sub;

    private String name;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
