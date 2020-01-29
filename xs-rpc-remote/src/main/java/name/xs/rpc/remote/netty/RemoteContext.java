package name.xs.rpc.remote.netty;

import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.enums.ThreadNameEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.common.utils.XsRpcThreadFactory;
import name.xs.rpc.remote.Client;

import java.util.Map;
import java.util.concurrent.*;

/**
 * create by xs
 * create time:2020-01-27 15:06:53
 */
public class RemoteContext {
    private static RemoteContext r = new RemoteContext();
    public static RemoteContext instance() {return r;}

    private Map<String, Client> clientMap = new ConcurrentHashMap<>();
    /**
     * 远程调用线程池
     */
    private ThreadPoolExecutor requestThreadPool = new ThreadPoolExecutor(Constant.CPU_NUM > 1 ? 3 : 0,
            Constant.CPU_NUM * 2,
            5,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            new XsRpcThreadFactory(ThreadNameEnum.CLIENT_REQUEST),
            (r, executor) -> {
                throw new XsRpcException(ErrorEnum.NETTY_03);
            });

    private Map<String, RequestingDto> requestingMap = new ConcurrentHashMap<>();

    public boolean addClient(String host, Integer port, Client client) {
        clientMap.putIfAbsent(host + ":" + port, client);
        return true;
    }

    public void removeClient(String host, Integer port) {
        clientMap.remove(host + ":" + port);
    }

    public Client getClient(String host, Integer port) {
        return clientMap.get(host + ":" + port);
    }
    public ThreadPoolExecutor getRequestThreadPool() {
        return requestThreadPool;
    }

    public boolean addRequestingDto(RequestingDto dto) {
        if (dto == null) {
            return false;
        }
        this.requestingMap.putIfAbsent(dto.getRequestMessage().getSessionId(), dto);
        return true;
    }

    public void requestFinish(RequestingDto dto) {
        if (dto == null) {
            return;
        }
        this.requestingMap.remove(dto.getRequestMessage().getSessionId());
    }

    public RequestingDto getRequestingDto(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return this.requestingMap.get(sessionId);
    }
}
