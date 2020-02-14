package name.xs.rpc.common.beans.protocol;

import java.net.ProtocolException;

/**
 * @author xs
 * create time:2019-07-2019-07-09 16:07:23
 */
public interface Decoder {

    Message decode(byte[] data) throws ProtocolException;

    int getHeaderLength();

    int getBodyLength(byte[] headerArr);

    int getLengthFieldOffset();
}
