package name.xs.rpc.common.event;

/**
 * 事件总线，xsRpc中的事件发布-订阅总线，会放在xsRpc中。
 *
 * @author xs
 * create time:2020-02-18 16:55:43
 */
public interface EventBus {

    /**
     * 发布事件
     * @param event 事件
     */
    boolean publish(Event<?> event);

    /**
     * 订阅事件
     * @param eventEnum 事件
     * @param eventListener 监听器
     */
    boolean subscribe(EventEnum eventEnum, EventListener eventListener);

    /**
     * 取消订阅
     * @param eventEnum 事件
     * @param eventListener 监听器
     */
    boolean unsubscribe(EventEnum eventEnum, EventListener eventListener);
}
