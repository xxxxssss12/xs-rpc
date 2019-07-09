package org.xs.rpc.protocol.xsp;

import org.xs.rpc.common.utils.ByteArrUtils;
import org.xs.rpc.protocol.Decoder;
import org.xs.rpc.protocol.Message;

import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;

/**
 * create by xs
 * create time:2019-07-09 16:16:58
 */
public class XspDecoder implements Decoder {

    @Override
    public Message decode(byte[] data) throws ProtocolException {
        // 包长校验
        if (data == null || data.length < XspConstant.HEAD_LENGHT) {
            throw new ProtocolException("data package error: package is too short.length:" + (data == null?0:data.length));
        }
        byte tag = data[XspConstant.PackageDef.TAG.getIndex()];
        byte encode = data[XspConstant.PackageDef.ENCODE.getIndex()];
        byte encrypt = data[XspConstant.PackageDef.ENCRYPT.getIndex()];
        byte extend1 = data[XspConstant.PackageDef.EXTEND1.getIndex()];
        byte extend2 = data[XspConstant.PackageDef.EXTEND2.getIndex()];
        byte[] sessionByte = ByteArrUtils.getBytes(data, XspConstant.PackageDef.SESSIONID.getIndex(), XspConstant.PackageDef.SESSIONID.getOffset());
        try {
            String sessionId = new String(sessionByte, XspConstant.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new ProtocolException(e.getMessage());
        }
        int length = ByteArrUtils.readInt(data, XspConstant.PackageDef.LENGTH.getIndex());
        int command = ByteArrUtils.readInt(data, XspConstant.PackageDef.COMMAND.getIndex());
        // TODO
        return null;
    }
}
