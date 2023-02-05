### 这是整合 Sentinel 源码模式启动的方式整合 Sentinel 服务到自己微服务示例项目

#### 源码来自 alibaba 官方开源项目： [Sentinel](https://github.com/alibaba/Sentinel)
#### 文献参考 ：[Sentinel官方文档](https://sentinelguard.io/zh-cn/)

#### 申明：此模式仅仅适用于开发环境，便于开发者灵活使用, 正式环境请直接使用 Nacos 官方镜像进行发布部署

#### [参考个人博客](https://www.cnblogs.com/Alay/p/16839285.html) 囊括了整合加自定义持久化流控规则

### 一 、整合说明
#### 1、官方源码下载 [点击跳转](https://github.com/alibaba/Sentinel/tags) (该示例以最新发行版 1.8.6 为例)
![download.png](images%2Fdownload.png)

#### 2、解压缩，并使用 IDEA 打开
![unzip&open.png](images%2Funzip%26open.png)

#### 3、整合 sentinel 官方源码到项目中
##### 和 整合 Nacos 类似，源码整合，依然只需要 sentinel-dashboard 模块即可,将源码中的 sentinel-dashboard 模块拷贝到自己项目中进行使用
####
###### 官方源码项目: 
![integration01.png](images%2Fintegration01.png)
##### 拷贝模块源码，整合到自己的项目
![integration02.png](images%2Fintegration02.png)

#### 4、配置文件修改(添加，示例中使用的是 yml 类型)
![main_config.png](images%2Fmain_config.png)
```yaml
server:
  port: ${TENDERING_PORT:8858}
  servlet:
    encoding:
      force: true

spring:
  application:
    name: ${@artifactId@:iserver-sentinel}

  # 引入Redis配置独立配置 /resource/application-redis.yml
  profiles:
    include: redis

  cloud:
    nacos:
      discovery:
        # 这里是您的 Nacos 相关连接信息，如：server-addr: 127.0.0.1:8848,一下配置我配置了 hosts 文件和环境变量方式
        server-addr: ${NACOS_HOST:iserver-nacos}:${NACOS_PORT:8848}

management:
  endpoints:
    web:
      exposure:
        include: '*'
  endpoint:
    health:
      show-details: ALWAYS
logging:
  level:
    org:
      springframework:
        web: info
  file:
    name: ${user.home}/logs/csp/sentinel-dashboard.log
  pattern:
    file: '%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n'

auth:
  # sentinel UI管理台登录的账号和密码，默认账号和密码都是 sentinel 
  username: iserver
  password: your_sentinel_password
  filter:
    exclude-urls: /,/auth/login,/auth/logout,/registry/machine,/version,/actuator/**,/details
    exclude-url-suffixes: htm,html,js,css,map,ico,ttf,woff,png

sentinel:
  dashboard:
    version: ${@project.version@:1.8.6}
```

#### 4、启动服务
###### springboot项目,运行主启动类即可项目
![start.png](images%2Fstart.png)

### 二、 流控规则自定义持久化
###### 说明可参看个人博客 [sentinel流控规则自定义持久化](https://www.cnblogs.com/Alay/p/16839285.html)
###### 涉及代码包截图
![in_redis.png](images%2Fin_redis.png)