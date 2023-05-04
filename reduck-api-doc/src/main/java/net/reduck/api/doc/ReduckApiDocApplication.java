package net.reduck.api.doc;

import net.reduck.api.doc.descriptor.Agent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReduckApiDocApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        SpringApplication.run(ReduckApiDocApplication.class, args);
        Agent.print();
//        classLoader.loadClass("net.reduck.NotFound");
    }

}
