package org.xs.rpc.test.netty.protocol;

import org.xs.rpc.protocol.Decoder;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;
import org.xs.rpc.protocol.MessageBuilder;
import org.xs.rpc.protocol.jlsp.JlspConstant;
import org.xs.rpc.protocol.jlsp.JlspDecoder;
import org.xs.rpc.protocol.jlsp.JlspEncoder;
import org.xs.rpc.protocol.jlsp.JlspMessageBuilder;

import java.util.Properties;

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
