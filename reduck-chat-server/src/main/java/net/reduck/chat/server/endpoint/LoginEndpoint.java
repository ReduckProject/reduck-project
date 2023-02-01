package net.reduck.chat.server.endpoint;

import net.reduck.chat.server.annotation.Endpoint;
import net.reduck.chat.server.annotation.EndpointBody;
import net.reduck.chat.server.annotation.EndpointMapping;
import net.reduck.chat.server.pojo.LoginTO;
import net.reduck.chat.server.pojo.LoginVO;
import net.reduck.chat.server.pojo.ResponseVO;

import java.util.ArrayList;

/**
 * @author Reduck
 * @since 2023/1/9 14:13
 */
@Endpoint
public class LoginEndpoint {

    @EndpointMapping(value = "/login")
    public ResponseVO<LoginVO> login(@EndpointBody LoginTO to, String username) {
        LoginVO vo = new LoginVO();
        vo.setUsername("Reducksco");
        vo.setGroupList(new ArrayList<>());
        return ResponseVO.ok(vo);
    }
}
