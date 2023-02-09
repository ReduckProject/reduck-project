package net.reduck.jpa.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Reduck
 * @since 2022/11/15 18:25
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Value("${token.allow.url:false}")
    private boolean tokenAllowUrl;
    @Value("${isVerifyUrlAuthority:false}")
    private boolean isVerifyUrlAuthority;
    @Value("${token.allow.apiDocUrls:/swagger-resources/**,/webjars/**,/v2/**,/swagger-ui.html,/META-INF/resources/**,/webjars/**,/META-INF/resources/webjars/**}")
    private String apiDocUrls;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        if(!"/error".equals(httpServletRequest.getRequestURI()) && httpServletRequest.getRequestURI().startsWith("/")){
            System.out.println(httpServletRequest.getRequestURI());
        }
//        if (!(object instanceof HandlerMethod)) {
//            return true;
//        } else {
//            int secondSlash = httpServletRequest.getRequestURI().indexOf("/", 1);
//            String uriStart = secondSlash > 0 ? httpServletRequest.getRequestURI().substring(0, secondSlash) : httpServletRequest.getRequestURI();
//            if (this.apiDocUrls.contains(uriStart)) {
//                return true;
//            } else {
//                String token = httpServletRequest.getHeader("token");
//                if (token == null) {
//                    logger.debug("header 中没有token....");
//                    if (!this.tokenAllowUrl) {
//                        httpServletResponse.setStatus(401);
//                        return false;
//                    }
//
//                    token = httpServletRequest.getParameter("token");
//                    if (StringUtils.isEmpty(token)) {
//                        httpServletResponse.setStatus(401);
//                        return false;
//                    }
//                }
//
//            }
//        }

//        httpServletResponse.setStatus(401);
//        return false;

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug("调用 接口返回:" + httpServletRequest.getRequestURL() + " ");
    }
}