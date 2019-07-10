package org.xs.rpc.test.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.xs.rpc.protocol.Decoder;

import java.util.List;

/**
 * create by xs
 * create time:2019-07-10 20:44:08
 */
public class DecoderAdapter extends ByteToMessageDecoder {
    private Decoder decoder;

    public DecoderAdapter(Decoder decoder) {
        this.decoder = decoder;
    }
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        
    }
}
