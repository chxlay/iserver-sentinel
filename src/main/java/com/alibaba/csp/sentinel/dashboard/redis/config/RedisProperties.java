package com.alibaba.csp.sentinel.dashboard.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author Alay
 * @date 2021-09-02 01:51
 */
@ConfigurationProperties(prefix = "sentinel.redis")
public class RedisProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    private String host = "localhost";
    /**
     * 端口，默认6379
     */
    private int port = 6379;
    /**
     * 超时，默认2000
     */
    private int timeout = 2000;
    /**
     * 连接超时，默认timeout
     */
    private int connectionTimeout = 2000;
    /**
     * 读取超时，默认timeout
     */
    private int soTimeout = 2000;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库序号，默认0
     */
    private int database = 15;
    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * SSL连接，默认false
     */
    private Boolean ssl = false;


    // 以下代码全部是 Getter  和 Setter 可以忽略
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public String getPassword() {
        return password;
    }

    public int getDatabase() {
        return database;
    }

    public String getclientName() {
        return clientName;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setclientName(String clientName) {
        this.clientName = clientName;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }
}