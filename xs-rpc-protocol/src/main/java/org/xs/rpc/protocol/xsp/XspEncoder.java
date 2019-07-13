package org.xs.rpc.protocol.xsp;

import org.xs.rpc.common.exceptions.ProtocolException;
import org.xs.rpc.common.utils.ByteArrUtils;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by xs
 * create time:2019-07-09 16:16:52
 */
public class XspEncoder implements Encoder {
    @Override
    public byte[] encode(Message msg) {
        XspMessage xspMsg = (XspMessage) msg;
        XspHeader header = xspMsg.getHeader();
        int size = header.getLength() + 45;
        if (size > XspConstant.MAX_LENGTH) {
            throw new ProtocolException("package size too large! size=" + size);
        }
        List<Byte> headers = new ArrayList<>(45);
        headers.add(header.getTag());
        headers.add(header.getEncode());
        headers.add(header.getEncrypt());
        headers.add(header.getExtend1());
        headers.add(header.getExtend2());
        headers.addAll(Arrays.asList(ByteArrUtils.intToBytes(header.getLength())));
        headers.addAll(Arrays.asList(ByteArrUtils.intToBytes(header.getCammand())));
        Byte[] headerArr = new Byte[45];
        headers.toArray(headerArr);
        byte[] bodyArr = null;
        try {
            bodyArr = xspMsg.getData().getBytes(XspConstant.CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new ProtocolException(e.getMessage());
        }
        // 拼接
        byte[] datas = new byte[size];
        for (int i=0; i<headerArr.length; i++) {
            datas[i] = headerArr[i];
        }
        for (int i=0; i<bodyArr.length; i++) {
            datas[i + XspConstant.PackageDef.DATA.getIndex()] = bodyArr[i];
        }
        return datas;
    }
}
