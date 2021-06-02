package team.alabs.wso3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class Wso3Application {
    public static void main(String[] args) {
        SpringApplication.run(Wso3Application.class, args);
    }
}
