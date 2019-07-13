package org.xs.rpc.protocol.xsp;

import org.xs.rpc.common.utils.Base64Utils;
import org.xs.rpc.common.utils.ByteArrUtils;
import org.xs.rpc.protocol.Header;
import org.xs.rpc.protocol.Message;
import org.xs.rpc.protocol.MessageBuilder;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * create by xs, 2019-07-13 15:24
 */
public class XspMessageBuilder implements MessageBuilder {

    public Message buildMessage(String data) {
        try {
            byte[] bodyArr = Base64Utils.encode(data, XspConstant.CHARSET);
            XspHeader header = new XspHeader(XspConstant.PACKAGE_TAG,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    ByteArrUtils.ZERO_ASCII,
                    UUID.randomUUID().toString().replaceAll("-", ""), bodyArr.length, ByteArrUtils.ZERO_ASCII);
            Message msg = new XspMessage(header, data);
            return msg;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
