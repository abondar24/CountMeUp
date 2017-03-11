package org.abondar.experimental.countmeup.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;



@Configuration
@SpringBootApplication
@EnableAutoConfiguration
public class CountApplication {

    static Logger logger = LoggerFactory.getLogger(CountApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CountApplication.class,args);
        logger.info("Application has started");
    }
}
