package com.alibaba.csp.sentinel.dashboard.redis.publisher;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.Constants;
import org.springframework.stereotype.Component;

/**
 * 发布推送 流控规则到 Redis 中
 *
 * @author Alay
 * @date 2022-09-25 18:16
 */
@Component("redisFlowRulePublisher")
public class RedisFlowRulePublisher extends AbstractRedisRulePublisher<FlowRuleEntity> {

    @Override
    protected String appRuleKey() {
        return Constants.APP_RULE_FLOW_KEY;
    }

    @Override
    protected String allRuleKey() {
        return Constants.ALL_RULE_FLOW_KEY;
    }
}
