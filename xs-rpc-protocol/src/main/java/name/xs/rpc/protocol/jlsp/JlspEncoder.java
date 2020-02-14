package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.common.beans.protocol.Encoder;
import name.xs.rpc.common.beans.protocol.Message;

import java.net.ProtocolException;

/**
 * @author xs
 * create time: 2019-07-14 22:13
 */
public class JlspEncoder implements Encoder {
    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        try {
            String str = msg.getData();
            String sessionId = msg.getSessionId();
            byte[] dataArr = str.getBytes(JlspConstant.CHARSET);
            byte[] sessionArr = sessionId.getBytes(JlspConstant.CHARSET);
            byte[] result = new byte[sessionId.length() + dataArr.length + JlspConstant.SEPERATOR.length];
            System.arraycopy(sessionArr, 0, result, 0, sessionArr.length);
            System.arraycopy(dataArr, 0, result, sessionArr.length, dataArr.length);
            System.arraycopy(JlspConstant.SEPERATOR, 0, result, dataArr.length + sessionArr.length, JlspConstant.SEPERATOR.length);
            return result;
        } catch (Exception e) {
            throw new ProtocolException(e.getMessage());
        }
    }
}
