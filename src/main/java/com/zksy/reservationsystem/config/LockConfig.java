package com.zksy.reservationsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁配置类
 *
 * @author kkkoke
 * @since 2022/11/22
 */
@Configuration
public class LockConfig {

    @Bean
    ReentrantLock reentrantLock() {
        return new ReentrantLock();
    }
}
