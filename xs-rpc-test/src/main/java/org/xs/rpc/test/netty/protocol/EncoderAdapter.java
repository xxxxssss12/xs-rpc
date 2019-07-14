package org.xs.rpc.test.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;

/**
 * create by xs
 * create time:2019-07-10 20:44:00
 */
public class EncoderAdapter extends MessageToByteEncoder<Message> {
    private Encoder encoder;

    public Encoder getEncoder() {
        return encoder;
    }

    public EncoderAdapter(Encoder encoder) {
        this.encoder = encoder;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        System.out.println("do encoder..msg=" + msg.getData());
        byte[] bytes = encoder.encode(msg);
        out.writeBytes(bytes);
    }
}
