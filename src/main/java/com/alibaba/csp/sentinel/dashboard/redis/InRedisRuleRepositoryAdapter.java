package com.alibaba.csp.sentinel.dashboard.redis;

import com.alibaba.csp.sentinel.dashboard.controller.FlowControllerV1;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.redis.config.RedisIdGenerator;
import com.alibaba.csp.sentinel.dashboard.redis.provider.AbstractRedisRuleProvider;
import com.alibaba.csp.sentinel.dashboard.redis.publisher.AbstractRedisRulePublisher;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 Redis 的规则存储
 *
 * @author Alay
 * @date 2022-09-25 23:48
 */
public abstract class InRedisRuleRepositoryAdapter<T extends RuleEntity> implements RuleRepository<T, Long> {
    protected final Logger logger = LoggerFactory.getLogger(FlowControllerV1.class);


    protected final AbstractRedisRulePublisher rulePublisher;
    protected final AbstractRedisRuleProvider ruleProvider;
    @Autowired
    protected RedisIdGenerator redisIdGenerator;


    public InRedisRuleRepositoryAdapter(AbstractRedisRulePublisher rulePublisher, AbstractRedisRuleProvider ruleProvider) {
        this.rulePublisher = rulePublisher;
        this.ruleProvider = ruleProvider;
    }

    /**
     * 保存 规则
     *
     * @param entity
     * @return
     */
    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(nextId());
        }
        entity = this.preProcess(entity);
        //List<T> entities = this.findAllByApp(entity.getApp());
        // entities.add(entity);
        // 保存到 Redis
        rulePublisher.publish(entity.getApp(), List.of(entity));
        return entity;
    }

    /**
     * 批量保存(从持久化存储中加载来的数据存储到 内存中)
     *
     * @param rules
     * @return
     */
    @Override
    public List<T> saveAll(List<T> rules) {
        // 从 Redis 中处理
        List<T> entities = new ArrayList<>();
        for (T rule : rules) {
            T entity = this.save(rule);
            entities.add(entity);
        }
        // 从 程序内存中处理
        // List<FlowRuleEntity> flowRuleEntities = repository.saveAll(rules);
        return entities;
    }

    /**
     * 删除规则
     *
     * @param id
     * @return
     */
    @Override
    public T delete(Long id) {
        // 先查询出 规则实体对象
        T entity = (T) ruleProvider.findById(id);
        logger.warn("##### 删除规则: {}", JSON.toJSONString(entity));
        rulePublisher.delete(entity);
        // 内存中删除
        // entity = repository.delete(id);
        return entity;
    }

    /**
     * 指定Id 查询
     *
     * @param id
     * @return
     */
    @Override
    public T findById(Long id) {
        // 或者从程序内存中获取
        // FlowRuleEntity entity = repository.findById(id);
        // 从 Redis 中获取
        T entity = (T) ruleProvider.findById(id);
        return entity;
    }

    /**
     * 按条件查询
     *
     * @param machineInfo
     * @return
     */
    @Override
    public List<T> findAllByMachine(MachineInfo machineInfo) {
        // 从内存中获取
        // List<FlowRuleEntity> allByMachine = repository.findAllByMachine(machineInfo);
        // 从Redis 中获取
        List<T> allByMachine = ruleProvider.findAllByMachine(machineInfo);
        return allByMachine;
    }

    /**
     * 通过 appName 查询
     *
     * @param appName valid app name
     * @return
     */
    @Override
    public List<T> findAllByApp(String appName) {
        List<T> rules = ruleProvider.getRules(appName);
        // 或者村内存中获取
        // List<FlowRuleEntity> rules = repository.findAllByApp(appName);
        return rules;
    }


    protected T preProcess(T entity) {
        return entity;
    }


    /**
     * Get next unused id.
     *
     * @return next unused id
     */
    abstract protected long nextId();
}
