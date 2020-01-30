package name.xs.rpc.remote.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.protocol.Encoder;
import name.xs.rpc.protocol.Message;

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
        Constant.LOG.debug("[EncoderAdapter] do encoder..msg={}, sessionId={}", msg.getData(), msg.getSessionId());
        byte[] bytes = encoder.encode(msg);
        out.writeBytes(bytes);
    }
}
