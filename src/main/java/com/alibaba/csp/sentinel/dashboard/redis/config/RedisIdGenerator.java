package com.alibaba.csp.sentinel.dashboard.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * 流控规则Id 生成器
 *
 * @author Alay
 * @date 2022-09-25 18:10
 */
@Component
public class RedisIdGenerator {

    @Autowired
    private RedisClient client;

    public long nextId(String key) {
        Jedis jedis = null;
        try {
            jedis = client.getJedis();
            return jedis.incrBy(key, 1);
        }finally {
            client.close(jedis);

        }
    }
}
