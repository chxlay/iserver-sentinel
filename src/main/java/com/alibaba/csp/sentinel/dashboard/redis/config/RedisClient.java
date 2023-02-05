package com.alibaba.csp.sentinel.dashboard.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Sentinel 整合 Redis 持久化流控规则
 *
 * @author Alay
 * @date 2022-09-25 18:07
 */
@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
public class RedisClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisProperties properties;

    private JedisPool jedisPool;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        jedisPool = new JedisPool(config,
                // 地址
                properties.getHost(),
                // 端口
                properties.getPort(),
                // 连接超时
                properties.getConnectionTimeout(),
                // 读取数据超时
                properties.getSoTimeout(),
                // 密码
                properties.getPassword(),
                // 默认数据库序号
                properties.getDatabase(),
                // 客户端名
                properties.getclientName(),
                // 是否使用SSL
                properties.getSsl(),
                // SSL相关，使用默认
                null, null, null);
        logger.info("Sentinel 中整合的 Redis 客户端工具 Jedis 配置完成");
        return jedisPool;
    }


    public Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(properties.getDatabase());
        return jedis;
    }

    public void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

}