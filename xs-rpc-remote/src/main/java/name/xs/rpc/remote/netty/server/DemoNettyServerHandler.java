package name.xs.rpc.remote.netty.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.common.beans.CommonRequest;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.context.ProxyContext;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.common.utils.TypeConvertUtils;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.xsp.XspHeader;
import name.xs.rpc.protocol.xsp.XspMessage;
import name.xs.rpc.protocol.xsp.XspMessageBuilder;
import name.xs.rpc.remote.ServerHandler;
import name.xs.rpc.remote.netty.protocol.ProtocolContext;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class DemoNettyServerHandler extends ChannelInboundHandlerAdapter implements ServerHandler {

//    private ProviderHandler providerHandler;
    private AtomicInteger requestCount = new AtomicInteger(0);
    /**
     * 客户端连接成功时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Constant.LOG.info("[DemoNettyServerHandler] 客户端连入：" + ctx.channel().remoteAddress().toString());
    }
    // 接受请求后处理类
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String sessionId = null;
        try {
            if (msg instanceof Message) {
                Message msg1 = (Message) msg;
                Constant.LOG.debug("[DemoNettyServerHandler] 客户端发送数据：" + JSON.toJSONString(msg));
                sessionId = msg1.getSessionId();
                CommonRequest request = JSON.parseObject(msg1.getData(), CommonRequest.class);
                CommonResult result = doInvoke(request);
                //此处写接收到客户端请求后的业务逻辑
                String content = JSON.toJSONString(result);


                Message message = ProtocolContext.getMessageBuilder().buildMessage(content, sessionId);
                ctx.writeAndFlush(message);
                Constant.LOG.debug("[DemoNettyServerHandler] 服务端响应：" + message.getData());
            } else {
                throw new XsRpcException(ErrorEnum.SERVER_01);
            }
        } catch (Exception e) {
            Constant.LOG.error("[DemoNettyServerHandler] server invoke error", e);
            CommonResult result = new CommonResult();
            XsRpcExceptionSerialize serialize = new XsRpcExceptionSerialize();
            serialize.setClassName(e.getClass().getName());
            serialize.setMessage(e.getMessage());
            result.setException(serialize);
            Message message = null;
            if (sessionId != null) {
                message = ProtocolContext.getMessageBuilder().buildMessage(JSON.toJSONString(result), sessionId);
            } else {
                message = ProtocolContext.getMessageBuilder().buildMessage(JSON.toJSONString(result));
            }
            ctx.writeAndFlush(message);
            Constant.LOG.debug("[DemoNettyServerHandler] 服务端响应：" + message.getData());
        }
    }

    private CommonResult doInvoke(CommonRequest request) throws Exception {
        if (request == null) {
            throw new XsRpcException(ErrorEnum.SERVER_01);
        }
        String interfaseName = request.getServiceInterfaceName();
        String methodName = request.getMethodName();
        if (interfaseName == null || interfaseName.length() == 0
            || methodName == null || methodName.length() == 0) {
            throw new XsRpcException(ErrorEnum.SERVER_01);
        }
        Class<?>[] paramTypeArr = TypeConvertUtils.strArr2ClassArr(request.getParameterTypes());
        Object[] argArr = TypeConvertUtils.strArr2objArr(request.getArguments(), paramTypeArr);
        Class<?> interfase = Class.forName(interfaseName);
        Method method = interfase.getMethod(methodName, paramTypeArr);
        if (method == null) {
            throw new XsRpcException(ErrorEnum.SERVER_02);
        }
        Object instance = ProxyContext.instance().getLocalProxyObjMap().get(interfaseName);
        if (instance == null) {
            throw new XsRpcException(ErrorEnum.SERVER_02);
        }
        Object returnResult = method.invoke(instance, argArr);
        CommonResult result = new CommonResult();
        result.setValue(returnResult);
        return result;
    }

    //读取完成后处理方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }

    //异常捕获处理方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
