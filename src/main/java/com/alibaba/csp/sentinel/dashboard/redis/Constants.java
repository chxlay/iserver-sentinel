package com.alibaba.csp.sentinel.dashboard.redis;

/**
 * 常量
 *
 * @author Alay
 * @date 2022-09-25 18:09
 */
public interface Constants {
    /**
     * 流控规则 存储的key,基于 appName 的 key
     */
    String APP_RULE_FLOW_KEY = "sentinel:flow:rule:";
    /**
     * 所有规则存储的 key
     */
    String ALL_RULE_FLOW_KEY = "sentinel:flow:rule:all";
    String ALL_RULE_FLOW_GATEWAY_KEY = "sentinel:flow:rule:all";
    /**
     * 流控规则数量
     */
    String RULE_FLOW_CHANNEL_PREFIX = "sentinel:flow:channel:";
    /**
     * 流控规则自增Id 的记录
     */
    String RULE_FLOW_RULE_ID_KEY = "sentinel:flow:id:rule";
    /**
     * 网关流控规则的自增Id
     */
    String RULE_FLOW_GATEWAY_ID_KEY = "sentinel:flow:id:gateway";
}
