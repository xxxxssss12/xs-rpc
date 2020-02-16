package name.xs.rpc.proxy.invoke;

import com.alibaba.fastjson.JSON;
import name.xs.rpc.common.beans.CommonRequest;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.Result;
import name.xs.rpc.common.utils.TypeConvertUtils;
import name.xs.rpc.config.ProviderMetadata;
import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.beans.remote.Client;
import name.xs.rpc.common.context.RemoteContext;
import name.xs.rpc.remote.netty.client.XsRpcNettyClient;
import name.xs.rpc.remote.netty.client.XsRpcNettyClientHandler;
import name.xs.rpc.common.context.ProtocolContext;

import java.lang.reflect.Method;

/**
 * @author xs
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
        // TODO 1. 从本地拉取所有providerMetaInfo
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
        Message msg = ProtocolContext.instance().getMessageBuilder().buildMessage(requestStr);
        return msg;
    }

    private ProviderMetadata testMetadata(String methodName) {
        ProviderMetadata metadata = new ProviderMetadata();
        metadata.setExportTimestamp(System.currentTimeMillis());
        metadata.setHost("127.0.0.1");
        metadata.setInterfaceName(insterfase.getName());
        metadata.setMethodsName(methodName);
        metadata.setPort(10020);
        metadata.setInterfaceVersion("0.0.1");
        return metadata;
    }

    private CommonRequest buildRequest(ProviderMetadata metadata, String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        CommonRequest request = new CommonRequest();
        request.setMethodName(methodName);
        request.setServiceId(metadata.getServiceId());
        request.setServiceInterfaceName(metadata.getInterfaceName());
        request.setSignatures("");
        request.setArguments(TypeConvertUtils.objArr2StrArr(arguments));
        request.setParameterTypes(TypeConvertUtils.classArr2StrArr(parameterTypes));
        return request;
    }

}
