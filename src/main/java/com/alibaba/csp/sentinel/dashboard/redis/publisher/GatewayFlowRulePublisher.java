package com.alibaba.csp.sentinel.dashboard.redis.publisher;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.Constants;
import org.springframework.stereotype.Component;

/**
 * @author Alay
 * @date 2022-09-26 00:34
 */
@Component
public class GatewayFlowRulePublisher  extends AbstractRedisRulePublisher<GatewayFlowRuleEntity> {

    @Override
    protected String appRuleKey() {
        return Constants.APP_RULE_FLOW_KEY;
    }

    @Override
    protected String allRuleKey() {
        return Constants.ALL_RULE_FLOW_GATEWAY_KEY;
    }
}
