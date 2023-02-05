package com.alibaba.csp.sentinel.dashboard.redis.publisher;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.config.RedisClient;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author Alay
 * @date 2022-09-26 00:18
 */
public abstract class AbstractRedisRulePublisher<T extends RuleEntity> implements DynamicRulePublisher<List<T>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisClient client;


    @Override
    public void publish(String appName, List<T> rules) {
        Assert.notNull(appName, "应用名称不能为空");
        Assert.notEmpty(rules, "策略规则不为空");
        logger.info("推送流控规则开始, 应用名: {}, 规则数量: {}", appName, rules.size());

        String ruleKey = this.appRuleKey() + appName;

        Jedis jedis = null;
        try {
            jedis = client.getJedis();
            for (T rule : rules) {
                // 以 Hash 形式存储到 应用规则中
                jedis.hset(ruleKey, rule.getId() + "", JSON.toJSONString(rule));
                // 以 Hash 形式存储 到所有规则中
                jedis.hset(this.allRuleKey(), rule.getId() + "", JSON.toJSONString(rule));
            }
        } finally {
            client.close(jedis);
        }
    }


    public void delete(T entity) {
        Jedis jedis = null;
        try {
            jedis = client.getJedis();
            // 从所有规则组中删除
            jedis.hdel(this.allRuleKey(), entity.getId() + "");
            // 从规则运用组中删除
            jedis.hdel(this.appRuleKey() + entity.getApp(), entity.getId() + "");
        } finally {
            client.close(jedis);
        }
    }


    /**
     * 基于 App 存储的 key
     *
     * @return
     */
    protected abstract String appRuleKey();

    /**
     * 全量规则存储的 key
     *
     * @return
     */
    protected abstract String allRuleKey();


}
