package net.reduck.chat.server.pojo;

import lombok.Data;

/**
 * @author Reduck
 * @since 2023/1/9 14:16
 */
@Data
public class ResponseVO<T> {

    private int code;

    private T data;

    private String message;

    public static <T> ResponseVO<T> ok(){
        return new ResponseVO<>();
    }

    public static <T> ResponseVO<T> ok(T data){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setData(data);

        System.out.println();
        return responseVO;
    }
}
