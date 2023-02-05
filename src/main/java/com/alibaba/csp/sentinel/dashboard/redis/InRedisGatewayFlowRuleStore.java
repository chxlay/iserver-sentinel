package com.alibaba.csp.sentinel.dashboard.redis;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.provider.GatewayFlowRuleProvider;
import com.alibaba.csp.sentinel.dashboard.redis.publisher.GatewayFlowRulePublisher;
import org.springframework.stereotype.Component;

/**
 * 网关规则存储
 *
 * @author Alay
 * @date 2022-09-25 23:47
 */
@Component
public class InRedisGatewayFlowRuleStore extends InRedisRuleRepositoryAdapter<GatewayFlowRuleEntity> {

    public InRedisGatewayFlowRuleStore(GatewayFlowRulePublisher rulePublisher, GatewayFlowRuleProvider ruleProvider) {
        super(rulePublisher, ruleProvider);
    }


    @Override
    protected long nextId() {
        return redisIdGenerator.nextId(Constants.RULE_FLOW_GATEWAY_ID_KEY);
    }

}
