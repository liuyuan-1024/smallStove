package com.bug.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.bug.**"})
// 开启事务管理
@EnableTransactionManagement
// 开启定时任务
@EnableScheduling
public class SmallStoveApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmallStoveApplication.class, args);
    }

}
