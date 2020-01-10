package name.xs.rpc.protocol;

import java.net.ProtocolException;

/**
 * create by xs
 * create time:2019-07-2019-07-09 16:07:23
 */
public interface Decoder {

    Message decode(byte[] data) throws ProtocolException;

    int getHeaderLength();

    int getBodyLength(byte[] headerArr);

    int getLengthFieldOffset();
}
