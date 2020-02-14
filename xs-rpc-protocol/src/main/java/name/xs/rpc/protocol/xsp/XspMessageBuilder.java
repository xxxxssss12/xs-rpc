package name.xs.rpc.protocol.xsp;

import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.common.utils.Base64Utils;
import name.xs.rpc.common.utils.ByteArrUtils;

import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.beans.protocol.MessageBuilder;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author xs
 * create time: 2019-07-13 15:24
 */
public class XspMessageBuilder implements MessageBuilder {

    private static final XspMessageBuilder i = new XspMessageBuilder();
    private XspMessageBuilder(){}
    public static XspMessageBuilder instance() {
        return i;
    }
    public Message buildMessage(String data) {
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        return buildMessage(data, sessionId);
    }

    public Message buildMessage(String data, String sessionId) {
        if (sessionId == null || sessionId.length() != 32) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_01);
        }
        try {
            byte[] bodyArr = Base64Utils.encode(data, XspConstant.CHARSET);
            XspHeader header = new XspHeader(XspConstant.PACKAGE_TAG,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    sessionId, bodyArr.length, ByteArrUtils.ZERO_ASCII);
            Message msg = new XspMessage(header, sessionId, data);
            return msg;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
