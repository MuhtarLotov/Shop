package team.alabs.wso3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import team.alabs.wso3.logging.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

//    @Bean
//    public LoggingAspect loggingAspect() {
//        return new LoggingAspect();
//    }
}
