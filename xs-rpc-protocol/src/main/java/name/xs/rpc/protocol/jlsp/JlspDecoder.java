package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.common.utils.ByteArrUtils;
import name.xs.rpc.protocol.Decoder;
import name.xs.rpc.protocol.Message;

import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;

/**
 * create by xs, 2019-07-14 22:13
 */
public class JlspDecoder implements Decoder {
    @Override
    public Message decode(byte[] data) throws ProtocolException {

        try {
            if (data.length < 4) {
                return new JlspMessage(new String(data, JlspConstant.CHARSET), "00000000000000000000000000000000");
            }
            byte[] seperator = ByteArrUtils.getBytes(data, data.length-4, 4);
            byte[] sessionId = ByteArrUtils.getBytes(data, 0, 32);
            if (ByteArrUtils.equals(seperator, JlspConstant.SEPERATOR)) {
                byte[] realData = ByteArrUtils.getBytes(data, 32, data.length - 36);
                return new JlspMessage(new String(realData, JlspConstant.CHARSET), new String(sessionId, JlspConstant.CHARSET));
            } else {
                byte[] realData = ByteArrUtils.getBytes(data, 32, data.length - 32);
                return new JlspMessage(new String(realData, JlspConstant.CHARSET), new String(sessionId, JlspConstant.CHARSET));
            }
        } catch (UnsupportedEncodingException e) {
            throw new ProtocolException(e.getMessage());
        }
    }

    @Override
    public int getHeaderLength() {
        return 0;
    }

    @Override
    public int getBodyLength(byte[] headerArr) {
        return 0;
    }

    @Override
    public int getLengthFieldOffset() {
        return 0;
    }
}
