package com.example.reservation.config;

import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Configuration
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() throws Exception {
        redisServer = new RedisServer(6379);
        redisServer.start();
        System.out.println("✅ Embedded Redis started on port 6379");
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            System.out.println("🟥 Embedded Redis stopped");
        }
    }
}
