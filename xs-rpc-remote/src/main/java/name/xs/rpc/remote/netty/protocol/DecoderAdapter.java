package name.xs.rpc.remote.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import name.xs.rpc.protocol.Decoder;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.xsp.XspDecoder;

import java.util.List;

/**
 * create by xs
 * create time:2019-07-10 20:44:08
 */
public class DecoderAdapter extends ByteToMessageDecoder {
    private Decoder decoder;

    public Decoder getDecoder() {
        return decoder;
    }

    public DecoderAdapter(Decoder decoder) {
        this.decoder = decoder;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Message msg = null;
        if (decoder instanceof XspDecoder) {
            int headLength = decoder.getHeaderLength();
            byte[] headArr = new byte[headLength];

            in.writeBytes(headArr);
            int bodyLength = decoder.getBodyLength(headArr);

            byte[] bodyArr = new byte[bodyLength];
            in.writeBytes(bodyArr);

            byte[] msgArr = new byte[headArr.length + bodyArr.length];
            System.arraycopy(headArr, 0, msgArr, 0, headArr.length);
            System.arraycopy(bodyArr, 0, msgArr, headArr.length, bodyArr.length);
            msg = decoder.decode(msgArr);
        } else {
            byte[] bt = new byte[in.capacity()];
            in.readBytes(bt);
            msg = decoder.decode(bt);
        }
        out.add(msg);      //Read integer from inbound ByteBuf, add to the List of decodec messages
        System.out.println("do encoder..msg=" + msg.getData());
    }
}
