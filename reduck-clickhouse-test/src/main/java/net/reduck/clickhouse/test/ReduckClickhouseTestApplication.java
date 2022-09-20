package net.reduck.clickhouse.test;

import net.reduck.clickhouse.test.repository.ClickhouseSimpleJpaRepository;
import net.reduck.clickhouse.test.repository.converter.ClickhouseTypeConverters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "net.reduck", repositoryBaseClass = ClickhouseSimpleJpaRepository.class)
//@EntityScan( basePackageClasses = Jsr310JpaConverters.class)
public class ReduckClickhouseTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReduckClickhouseTestApplication.class, args);
	}

}
