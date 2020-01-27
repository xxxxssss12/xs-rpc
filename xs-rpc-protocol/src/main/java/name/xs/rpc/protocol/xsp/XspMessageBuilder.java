package name.xs.rpc.protocol.xsp;

import name.xs.rpc.common.utils.Base64Utils;
import name.xs.rpc.common.utils.ByteArrUtils;

import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.MessageBuilder;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * create by xs, 2019-07-13 15:24
 */
public class XspMessageBuilder implements MessageBuilder {

    public Message buildMessage(String data) {
        try {
            byte[] bodyArr = Base64Utils.encode(data, XspConstant.CHARSET);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            XspHeader header = new XspHeader(XspConstant.PACKAGE_TAG,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    uuid, bodyArr.length, ByteArrUtils.ZERO_ASCII);
            Message msg = new XspMessage(header, uuid, data);
            return msg;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
