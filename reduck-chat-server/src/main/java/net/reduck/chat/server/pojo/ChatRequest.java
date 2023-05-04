package net.reduck.chat.server.pojo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Reduck
 * @since 2023/1/3 17:30
 */
@Data
public class ChatRequest<T> {

    private String endpoint;

    private String cookie;

    @Valid
    @NotNull
    private T data;

}
