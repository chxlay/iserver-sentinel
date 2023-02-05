package com.alibaba.csp.sentinel.dashboard.redis.provider;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.redis.config.RedisClient;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alay
 * @date 2022-09-25 23:58
 */
public abstract class AbstractRedisRuleProvider<T extends RuleEntity> implements DynamicRuleProvider<List<T>> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisClient client;

    @Override
    public List<T> getRules(String appName) {
        Assert.notNull(appName, "应用名称不能为空");
        logger.info("拉取redis流控规则开始: {}", appName);
        String key = this.appRuleKey() + appName;
        Jedis jedis = null;
        List<T> rules = new ArrayList<>();
        try {
            jedis = client.getJedis();
            Map<String, String> rulesMap = jedis.hgetAll(key);
            if (null != rulesMap && !rulesMap.isEmpty()) {
                for (Map.Entry<String, String> entry : rulesMap.entrySet()) {
                    rules.add(JSON.parseObject(entry.getValue(), this.genericsClazz()));
                }
            }
        } finally {
            client.close(jedis);
        }
        logger.info("拉取redis流控规则成功, 规则数量: {}", CollectionUtils.size(rules));
        return rules;
    }


    /**
     * 指定Id 查询应用
     *
     * @param id
     * @return
     */
    public T findById(Long id) {
        Jedis jedis = null;
        try {
            jedis = client.getJedis();
            // 从所有规则组中查找
            String ruleStr = jedis.hget(this.allRuleKey(), id + "");
            if (null == ruleStr) return null;
            return JSON.parseObject(ruleStr, this.genericsClazz());
        } finally {
            client.close(jedis);
        }
    }


    public List<T> findAllByMachine(MachineInfo machineInfo) {
        if (null == machineInfo) return null;
        Jedis jedis = null;
        try {
            jedis = client.getJedis();
            // 从所有规则组中查找
            List<T> rules = this.getRules(machineInfo.getApp());
            // 路由为空
            if (rules.isEmpty()) return rules;
            List<T> entities = new ArrayList<>(rules.size());
            for (T rule : rules) {
                MachineInfo compare = MachineInfo.of(rule.getApp(), rule.getIp(), rule.getPort());
                if (Objects.equals(compare, machineInfo)) {
                    entities.add(rule);
                }
            }
            return entities;
        } finally {
            client.close(jedis);
        }
    }


    /**
     * 泛型clazz
     *
     * @return
     */
    protected abstract Class<T> genericsClazz();

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
