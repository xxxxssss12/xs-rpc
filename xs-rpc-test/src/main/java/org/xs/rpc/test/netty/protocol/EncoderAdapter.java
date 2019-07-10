package org.xs.rpc.test.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.xs.rpc.common.exceptions.ProtocolException;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;
import org.xs.rpc.protocol.xsp.XspConstant;

/**
 * create by xs
 * create time:2019-07-10 20:44:00
 */
public class EncoderAdapter extends MessageToByteEncoder<Message> {
    private Encoder encoder;
    public EncoderAdapter(Encoder encoder) {
        this.encoder = encoder;
    }
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        byte[] bytes = encoder.encode(msg);
        if (bytes.length > XspConstant.MAX_LENGTH) {
            throw new ProtocolException("pkg too large!size = " + bytes.length);
        }
        out.writeBytes(bytes);
    }
}
