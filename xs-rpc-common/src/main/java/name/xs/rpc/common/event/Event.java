package name.xs.rpc.common.event;

import name.xs.rpc.common.event.EventEnum;

/**
 * 事件类
 *
 * @author xs
 * create time:2020-02-18 17:00:49
 */
public class Event<T> {

    private EventEnum eventEnum;

    private T data;

    public Event(EventEnum eventEnum, T data) {
        this.eventEnum = eventEnum;
        this.data = data;
    }

    public EventEnum getEventEnum() {
        return eventEnum;
    }

    public T getData() {
        return data;
    }
}
