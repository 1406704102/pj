# 这是一个集合示例

### config

springboot的一些配置

* cors 跨域


### koltin 语法

### satoken

sa-token 权限验证

* [文档](http://sa-token.dev33.cn/doc/#/)

### doubleDBConfig

多数据源
* 使用aop实现的多数据源实例
* 使用aop和redis实现的多数据库的事物
* 使用方法在jpa 包中的 实现类

### springSecurity
* springSecurity&jwt配置
* 根据注解判断方法是可以匿名访问
* 使用PreAuthorize("")验证权限

### jpa

* 动态查询
* 使用注解/原生sql查询
* 多对多映射关系
### nginx
* nginx.conf 配置文件(带负载均衡)
* 限流.txt 限流写法

### redis
 * redis 配置
 * StringRedisTemplate的各种操作
 * key的模糊查询
 * 使用@Cacheable 添加缓存

### docker
* docker-compose 创建容器
* docker 常用命令

### lottery 
* 使用redis 和 rocketMQ开发的抽奖

### wx
* util 微信相关工具类 (获取各种东西,公众号自定义分享签名)
* pay.xcx 小程序支付
* view.gzhAuthorize 公众号网页授权流程
    1. 根据缓存中是否有用户信息来判断用户是否授权过
    1. 使用钩子函数(除了授权页面 全都需要window.location.href = 跳转微信获取code 链接)
    1. 如果没有授权则使用 window.location.href = 跳转微信获取code 链接,并在缓存中保存想要去的路由
    1. 返回链接是vue 的前端项目的授权页面,获取微信传来的code,传到后台获取用户信息,成功后返回给授权页并存入缓存
    1. 保存成功后重定向到缓存中保存的路由
* view.gzhShare 微信公众号网页自定义分享 (引入 npm install weixin-js-sdk)
* view.xcxAuthorize 小程序授权
* view.webview webview 网页返回 微信小程序
* view.navigationBar navigationBar 滑动变色停留
* view.like 点赞
* view.myHttp 发起请求封装
* webview index.html 网页跳转小程序

### dy
* 抖音授权登录;上传发布视频

### obs

* 华为云obs

### util

* 一些工具类
  * 时间
  * 文件
  * ip
  * jackson
  * 二维码
  * 金钱

### linux

* 常用到的命令

### excel

* 上传

### file 文件操作

* md5

### sql

* sql 

### preventDuplication
* 使用  @PreventDuplication(value = "questionInfo.query",expireSeconds = 100)  防止重复提交