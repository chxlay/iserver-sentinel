package com.alibaba.csp.sentinel.dashboard.redis.provider;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.Constants;
import org.springframework.stereotype.Component;

/**
 * l拉取 Redis 中存储的流控规则
 *
 * @author Alay
 * @date 2022-09-25 18:14
 */
@Component("redisFlowRuleProvider")
public class RedisFlowRuleProvider extends AbstractRedisRuleProvider<FlowRuleEntity> {

    @Override
    protected Class<FlowRuleEntity> genericsClazz() {
        return FlowRuleEntity.class;
    }

    @Override
    protected String appRuleKey() {
        return Constants.APP_RULE_FLOW_KEY;
    }

    @Override
    protected String allRuleKey() {
        return Constants.ALL_RULE_FLOW_KEY;
    }
}
