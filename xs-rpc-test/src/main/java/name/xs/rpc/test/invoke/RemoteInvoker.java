package name.xs.rpc.test.invoke;

import com.alibaba.fastjson.JSON;
import name.xs.rpc.common.beans.CommonRequest;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.Result;
import name.xs.rpc.config.ProviderMetadata;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.xsp.XspMessageBuilder;
import name.xs.rpc.remote.Client;
import name.xs.rpc.remote.netty.RemoteContext;
import name.xs.rpc.remote.netty.client.XsRpcNettyClient;
import name.xs.rpc.remote.netty.client.XsRpcNettyClientHandler;
import name.xs.rpc.remote.netty.protocol.ProtocolContext;

import java.lang.reflect.Method;

/**
 * create by xs
 * create time:2020-01-26 20:22:05
 */
public class RemoteInvoker<T> extends AbstractProxyInvoker<T> {

    private Class<T> insterfase;
    public RemoteInvoker(Class<T> insterfase) {
        super();
        this.insterfase = insterfase;
    }

    @Override
    public Result invoke(Method method, Class<?>[] parameterTypes, Object[] arguments) throws Exception {
        String methodName = method.getName();
        // TODO 1. 从注册中心拉取所有providerMetaInfo
        // 2. 负载均衡策略选择一个provider
        ProviderMetadata metadata = testMetadata(methodName);
        // 3. 封装request
        CommonRequest request = buildRequest(metadata, methodName, parameterTypes, arguments);
        Client client = RemoteContext.instance().getClient(metadata.getHost(), metadata.getPort());
        if (client == null) {
            client = new XsRpcNettyClient(metadata.getHost(), metadata.getPort(), new XsRpcNettyClientHandler());
            RemoteContext.instance().addClient(metadata.getHost(), metadata.getPort(), client);
        }
        Message requestMsg = request2Msg(request);
        Message responseMsg = client.send(requestMsg, 5000L);
        RemoteContext.instance().requestFinish(RemoteContext.instance().getRequestingDto(requestMsg.getSessionId()));
        return msg2Result(responseMsg);
    }

    private Result msg2Result(Message responseMsg) {
        String responseStr = responseMsg.getData();
        Result rs = JSON.parseObject(responseStr, CommonResult.class);
        return rs;
    }

    private Message request2Msg(CommonRequest request) {
        String requestStr = JSON.toJSONString(request);
        Message msg = ProtocolContext.getMessageBuilder().buildMessage(requestStr);
        return msg;
    }

    private ProviderMetadata testMetadata(String methodName) {
        ProviderMetadata metadata = new ProviderMetadata();
        metadata.setExportTimestamp(System.currentTimeMillis());
        metadata.setHost("127.0.0.1");
        metadata.setInterfaceName(insterfase.getName());
        metadata.setMethodsName(methodName);
        metadata.setPort(10000);
        metadata.setInterfaceVersion("0.0.1");
        return metadata;
    }

    private CommonRequest buildRequest(ProviderMetadata metadata, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        CommonRequest request = new CommonRequest();
        request.setMethodName(methodName);
        request.setServiceId(metadata.getServiceId());
        request.setServiceInterfaceName(metadata.getInterfaceName());
        request.setSignatures("");
        request.setArguments(objArr2StrArr(arguments));
        request.setParameterTypes(classArr2StrArr(parameterTypes));
        return request;
    }

    private String[] classArr2StrArr(Class<?>[] parameterTypes) {
        if (parameterTypes != null && parameterTypes.length != 0) {
            String[] paramTypes = new String[parameterTypes.length];
            for (int i=0; i<parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (parameterType != null) {
                    paramTypes[i] = parameterType.getName();
                }
            }
            return paramTypes;
        } else {
            return new String[0];
        }
    }

    private String[] objArr2StrArr(Object[] arguments) {
        if (arguments != null && arguments.length > 0) {
            String[] args = new String[arguments.length];
            for (int i=0; i<arguments.length; i++) {
                Object arg = arguments[i];
                if (arg != null) {
                    args[i] = JSON.toJSONString(arg);
                } else {
                    args[i] = null;
                }
            }
            return args;
        } else {
            return new String[0];
        }
    }
}
