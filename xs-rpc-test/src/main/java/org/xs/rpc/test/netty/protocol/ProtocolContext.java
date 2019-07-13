package org.xs.rpc.test.netty.protocol;

import org.xs.rpc.protocol.Decoder;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;
import org.xs.rpc.protocol.MessageBuilder;

import java.util.Properties;

/**
 * create by xs
 * create time:2019-07-10 20:39:44
 */
public class ProtocolContext {

    private static EncoderAdapter encoder;
    private static DecoderAdapter decoder;
    private static Byte seperateCharacter;
    private static MessageBuilder messageBuilder;

    public ProtocolContext(EncoderAdapter encoder, DecoderAdapter decoder, Byte seperateCharacter, MessageBuilder builder) {
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

    public static Byte getSeperateCharacter() {
        return seperateCharacter;
    }

    public static MessageBuilder getMessageBuilder() {
        return messageBuilder;
    }
}
