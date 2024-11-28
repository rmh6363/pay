package com.pay.loggingconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class LoggingConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoggingConsumerApplication.class);
    }
}
