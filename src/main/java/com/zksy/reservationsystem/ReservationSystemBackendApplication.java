package com.zksy.reservationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ReservationSystemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationSystemBackendApplication.class, args);
    }

}
