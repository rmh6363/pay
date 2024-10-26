package com.pay.membership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MembershipApplication {
    public static void main(String[] args) {
        SpringApplication.run(MembershipApplication.class);
    }
}
