package name.xs.rpc.remote.netty.protocol;

import name.xs.rpc.common.context.ProtocolContext;
import name.xs.rpc.protocol.jlsp.JlspConstant;
import name.xs.rpc.protocol.jlsp.JlspDecoder;
import name.xs.rpc.protocol.jlsp.JlspEncoder;
import name.xs.rpc.protocol.jlsp.JlspMessageBuilder;

/**
 * 协议上下文初始化
 *
 * @author xs
 * create time:  2020/2/14 9:30
 */
public class ProtocolContextInit {

    public static void init() {
        ProtocolContext p = ProtocolContext.instance();
        if (p.isAlreadyInitialized()) {
            return;
        }
        p.setEncoder(new NettyEncoderAdapter(new JlspEncoder()));
        p.setDecoder(new NettyDecoderAdapter(new JlspDecoder()));
        p.setSeperateCharacter(JlspConstant.SEPERATOR);
        p.setMessageBuilder(new JlspMessageBuilder());
        p.setAlreadyInitialized(true);
    }
}
