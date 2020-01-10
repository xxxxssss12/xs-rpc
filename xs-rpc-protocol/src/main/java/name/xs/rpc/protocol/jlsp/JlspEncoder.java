package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.protocol.Encoder;
import name.xs.rpc.protocol.Message;

import java.net.ProtocolException;

/**
 * create by xs, 2019-07-14 22:13
 */
public class JlspEncoder implements Encoder {
    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        String str = msg.getData();
        byte[] dataArr = str.getBytes();
        byte[] result = new byte[dataArr.length + JlspConstant.SEPERATOR.length];
        System.arraycopy(dataArr, 0, result, 0, dataArr.length);
        System.arraycopy(JlspConstant.SEPERATOR, 0, result, dataArr.length, JlspConstant.SEPERATOR.length);
        return result;
    }
}
