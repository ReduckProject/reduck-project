package net.reduck.chat.server.handler;

import lombok.RequiredArgsConstructor;
import net.reduck.chat.server.annotation.Session;
import net.reduck.chat.server.dao.SessionDao;
import net.reduck.chat.server.pojo.ChatRequest;

import java.lang.annotation.Annotation;

/**
 * @author Reduck
 * @since 2023/1/9 16:05
 */
@RequiredArgsConstructor
public class SessionHandler implements AnnotationHandler {
    private final SessionDao sessionDao;

    public SessionHandler() {
        sessionDao = null;
    }

    @Override
    public Object handle(EndpointArgumentDescriptor descriptor, ChatRequest<byte[]> chatRequest) {
        return sessionDao.loadSession(chatRequest.getCookie());
    }

    @Override
    public Class<? extends Annotation> getSupportAnnotation() {
        return Session.class;
    }
}
