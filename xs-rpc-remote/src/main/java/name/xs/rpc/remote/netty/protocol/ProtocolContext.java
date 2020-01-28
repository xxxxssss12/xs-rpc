package name.xs.rpc.remote.netty.protocol;

import name.xs.rpc.protocol.MessageBuilder;
import name.xs.rpc.protocol.jlsp.JlspConstant;
import name.xs.rpc.protocol.jlsp.JlspDecoder;
import name.xs.rpc.protocol.jlsp.JlspEncoder;
import name.xs.rpc.protocol.jlsp.JlspMessageBuilder;

/**
 * create by xs
 * create time:2019-07-10 20:39:44
 */
public class ProtocolContext {

    private static EncoderAdapter encoder;
    private static DecoderAdapter decoder;
    private static byte[] seperateCharacter;
    private static MessageBuilder messageBuilder;

    public ProtocolContext(EncoderAdapter encoder, DecoderAdapter decoder, byte[] seperateCharacter, MessageBuilder builder) {
        ProtocolContext.encoder = encoder;
        ProtocolContext.decoder = decoder;
        ProtocolContext.seperateCharacter = seperateCharacter;
        ProtocolContext.messageBuilder = builder;
    }

    public static EncoderAdapter getEncoder() {
        return encoder;
    }

    public static DecoderAdapter getDecoder() {
        return decoder;
    }

    public static byte[] getSeperateCharacter() {
        return seperateCharacter;
    }

    public static MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }

    public static void init() {
        encoder = new EncoderAdapter(new JlspEncoder());
        decoder = new DecoderAdapter(new JlspDecoder());
        seperateCharacter = JlspConstant.SEPERATOR;
        messageBuilder = new JlspMessageBuilder();
    }
}
