# xs-rpc

旨在从零开始撸一个rpc框架，撸完可能加上服务注册发现、spring整合等上层环节。

## 什么是rpc？

RPC是指远程过程调用，也就是说两台服务器A，B，一个应用部署在A服务器上，想要调用B服务器上应用提供的函数/方法，
由于不在一个内存空间，不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。

## 怎么实现rpc

要实现一个RPC框架主要实现3点。

1. 方法映射-本地方法代理远程调用，通过此方法获取到此方法的远程信息（host+端口等）
2. 参数序列化/反序列化
3. 网络传输协议 


Call ID映射可以直接使用函数字符串，也可以使用整数ID。
映射表一般就是一个哈希表。序列化反序列化可以自己写，也可以使用Protobuf或者FlatBuffers之类的。
网络传输库可以自己写socket，或者用asio，ZeroMQ，Netty之类。


具体一点应该这么划分

* client初始化
* client调用rpc方法
* server初始化
* server接收rpc请求并调用本地方法

接下来要做的是梳理这4个步骤，抽取公共方法

### client 初始化 + 调用rpc方法

1. 加载服务提供方信息-host+端口
2. 通过引用列表配置，获取如下信息
    1. 服务名
    2. 服务提供方接收协议类型
    3. 服务超时时间
3. 对引用列表配置中的接口进行代理操作，获取其：
    1. 方法名
    2. 方法参数
    3. 方法返回类型
4. 配置通信框架
5. 通过通信框架与服务提供方建立长连接——至此初始化完毕。
6. 上层通过代理方法调用，触发远程rpc请求逻辑（代理消费方法程序流程）
    1. 封装通用参数数据data
    2. 转化成指定协议，encode
    3. 通过通信框架请求远端
    4. 接收到远端返回数据
    5. 按指定协议转化成通用参数数据data，decode
    6. 如果返回是exception，抛出异常
    7. 如果返回不是exception，返回。
    
### server初始化
1. 加载服务提供方信息-host+端口
2. 通过服务列表配置，获取如下信息
    1. 服务名
    2. 服务提供方接收协议类型
    3. 服务超时时间
3. 对服务列表配置中的接口进行代理操作，获取其：
    1. 方法名
    2. 方法参数
    3. 方法返回类型
    4. 方法实现类
4. 配置通信框架
5. 通过通信框架监听端口，等待请求进来
6. 具体请求处理逻辑：
    1. 数据包整合完整
    2. 按协议类型转化成通用参数数据，decode
    3. 通过代理类调用相应方法 invoke
    4. 拿到返回值，封装通用数据data
    5. 转化成指定协议
    6. 响应远程请求
    
    
### 结论
其中有几个明显公共的地方
* 都有配置信息
* 都需要通信框架
* 客户端和服务端需要约定好通信协议
* 数据格式需要统一，方便处理
* 都是接口+实现类的形式，接口暴露给客户端，都需要对接口进行代理（还有别的请求形式吗？）
* request和response可以封装，客户端和服务端都需要使用

### 工作步骤
1. 定义元数据（需要存储到注册中心的数据）
2. 定义服务提供方所需信息
3. 定义服务消费方所需信息
4. 定义服务提供方启动方案
5. 定义服务提供方启动步骤
6. 定义服务消费方启动方案
7. 定义服务消费方启动步骤