package com.alibaba.csp.sentinel.dashboard.redis;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.provider.RedisFlowRuleProvider;
import com.alibaba.csp.sentinel.dashboard.redis.publisher.RedisFlowRulePublisher;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import org.springframework.stereotype.Component;

/**
 * 居于Redis的规则存储
 *
 * @author Alay
 * @date 2022-09-25 21:06
 */
@Component
public class InRedisFlowRuleStore extends InRedisRuleRepositoryAdapter<FlowRuleEntity> {

    //@Autowired
    //private InMemoryRuleRepositoryAdapter<FlowRuleEntity> repository;

    public InRedisFlowRuleStore(RedisFlowRulePublisher rulePublisher, RedisFlowRuleProvider ruleProvider) {
        super(rulePublisher, ruleProvider);
    }

    public long nextId() {
        return redisIdGenerator.nextId(Constants.RULE_FLOW_RULE_ID_KEY);
    }


    protected FlowRuleEntity preProcess(FlowRuleEntity entity) {
        if (entity != null && entity.isClusterMode()) {
            ClusterFlowConfig config = entity.getClusterConfig();
            if (config == null) {
                config = new ClusterFlowConfig();
                entity.setClusterConfig(config);
            }
            // Set cluster rule id.
            config.setFlowId(entity.getId());
        }
        return entity;
    }

}
