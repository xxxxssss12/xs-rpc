package org.xs.rpc.test.netty.protocol;

import org.xs.rpc.protocol.Decoder;
import org.xs.rpc.protocol.Encoder;
import org.xs.rpc.protocol.Message;

/**
 * create by xs
 * create time:2019-07-10 20:39:44
 */
public class ProtocolContext {

    private EncoderAdapter encoder;
    private DecoderAdapter decoder;
    private Byte seperateCharacter;

    public ProtocolContext(EncoderAdapter encoder, DecoderAdapter decoder, Byte seperateCharacter) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.seperateCharacter = seperateCharacter;
    }

    public EncoderAdapter getEncoder() {
        return encoder;
    }

    public DecoderAdapter getDecoder() {
        return decoder;
    }

    public Byte getSeperateCharacter() {
        return seperateCharacter;
    }
}
