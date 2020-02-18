package name.xs.rpc.common.event;

import name.xs.rpc.common.constants.Constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xs
 * create time:2020-02-18 19:38:02
 */
public class DefaultEventBus implements EventBus {

    private Map<EventEnum, Map<String, EventListener>> listenerHolder = new ConcurrentHashMap<>();

    @Override
    public boolean publish(Event<?> event) {
        if (event == null || event.getEventEnum() == null) {
            Constant.LOG.error("event publish fail: wrong event");
            return false;
        }
        Map<String, EventListener> eventListeners = listenerHolder.get(event.getEventEnum());
        if (eventListeners == null || eventListeners.isEmpty()) {
            return true;
        }
        for (EventListener listener : eventListeners.values()) {
            try {
                listener.onEventPublish(event);
            } catch (Exception e) {
                Constant.LOG.error("event publish fail, listenerId=" + (listener == null ? "null" : listener.id), e);
            }
        }
        return true;
    }

    @Override
    public boolean subscribe(EventEnum eventEnum, EventListener eventListener) {
        if (eventEnum == null || eventListener == null || eventListener.id == null || eventListener.id.isEmpty()) {
            return false;
        }
        Map<String, EventListener> listenerMap = null;
        if (!listenerHolder.containsKey(eventEnum)) {
            listenerMap = listenerHolder.putIfAbsent(eventEnum, new ConcurrentHashMap<>());
        }
        listenerMap.putIfAbsent(eventListener.id, eventListener);
        return true;
    }

    @Override
    public boolean unsubscribe(EventEnum eventEnum, EventListener eventListener) {
        if (eventEnum == null || eventListener == null || eventListener.id == null || eventListener.id.isEmpty()) {
            return false;
        }
        if (!listenerHolder.containsKey(eventEnum)) {
            return true;
        }
        Map<String, EventListener> listenerMap = listenerHolder.get(eventEnum);
        if (listenerMap == null || listenerMap.isEmpty()) {
            return true;
        }
        listenerMap.remove(eventListener.id);
        return true;
    }

}
