package name.xs.rpc.common.event;

/**
 * 事件监听器
 *
 * @author xs
 * create time:2020-02-18 19:39:23
 */
public interface EventListener {
    // 用它防止重复订阅
    String id = null;

    void onEventPublish(Event<?> event);
}
