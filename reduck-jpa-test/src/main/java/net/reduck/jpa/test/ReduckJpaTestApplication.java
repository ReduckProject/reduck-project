package net.reduck.jpa.test;

import net.reduck.jpa.specification.JpaRepositoryExtendedImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "net.reduck")
@EnableJpaRepositories(basePackages = "net.reduck", repositoryBaseClass = JpaRepositoryExtendedImpl.class)
public class ReduckJpaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReduckJpaTestApplication.class, args);

        System.out.println("---------------------");
    }

}
