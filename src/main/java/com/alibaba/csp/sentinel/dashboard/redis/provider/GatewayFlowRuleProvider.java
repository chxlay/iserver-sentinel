package com.alibaba.csp.sentinel.dashboard.redis.provider;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.redis.Constants;
import org.springframework.stereotype.Component;

/**
 * 网关整合 sentinel 规则持久化查询服务
 *
 * @author Alay
 * @date 2022-09-26 00:30
 */
@Component
public class GatewayFlowRuleProvider extends AbstractRedisRuleProvider<GatewayFlowRuleEntity> {


    @Override
    protected Class<GatewayFlowRuleEntity> genericsClazz() {
        return GatewayFlowRuleEntity.class;
    }

    @Override
    protected String appRuleKey() {
        return Constants.APP_RULE_FLOW_KEY;
    }

    @Override
    protected String allRuleKey() {
        return Constants.ALL_RULE_FLOW_GATEWAY_KEY;
    }
}
