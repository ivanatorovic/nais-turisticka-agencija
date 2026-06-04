package com.example.analiza_aktivnosti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration(redisHost, redisPort);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager cacheManager() {

        RedisCacheConfiguration defaultConfig = createConfig(Duration.ofMinutes(10));

        RedisCacheConfiguration activityOccupancyConfig =
                createConfig(Duration.ofMinutes(10));

        RedisCacheConfiguration activityRevenueConfig =
                createConfig(Duration.ofMinutes(10));

        RedisCacheConfiguration arrangementRevenueStatisticsConfig =
                createConfig(Duration.ofMinutes(10));

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(defaultConfig)

                .withCacheConfiguration(
                        "activityOccupancy",
                        activityOccupancyConfig
                )

                .withCacheConfiguration(
                        "activityRevenue",
                        activityRevenueConfig
                )

                .withCacheConfiguration(
                        "arrangementRevenueStatistics",
                        arrangementRevenueStatisticsConfig
                )

                .build();
    }

    private RedisCacheConfiguration createConfig(Duration ttl) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues()
                .serializeValuesWith(
                        SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer()
                        )
                );
    }
}