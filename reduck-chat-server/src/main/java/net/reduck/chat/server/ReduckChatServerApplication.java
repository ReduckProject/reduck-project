package net.reduck.chat.server;

import net.reduck.chat.server.handler.AnnotationHandler;
import net.reduck.chat.server.handler.EndpointBodyHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ServiceLoader;

@SpringBootApplication
public class ReduckChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReduckChatServerApplication.class, args);
    }
}
