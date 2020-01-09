package org.xs.rpc.test.container;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * create by xs
 * create time:2019-11-29 15:24:57
 */
public class Container {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public Container start() {
        System.out.println("lock");
        lock.lock();
        try {
            // 1. 读取配置
            // 2. 服务提供者实例代理
            // 3. 服务消费者实例代理
            // 4. 监听端口
//            createServiceInstance();

            System.out.println("await");
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("over");
        return this;
    }

    public boolean stop() {
        return true;
    }
}
