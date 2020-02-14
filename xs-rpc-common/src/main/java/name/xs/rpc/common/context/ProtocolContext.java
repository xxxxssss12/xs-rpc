package name.xs.rpc.common.context;

import name.xs.rpc.common.beans.protocol.MessageBuilder;
import name.xs.rpc.common.beans.remote.DecoderAdapter;
import name.xs.rpc.common.beans.remote.EncoderAdapter;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;

/**
 * 远程通信协议上下文
 *
 * @author xs
 * create time:2019-07-10 20:39:44
 */
public class ProtocolContext {

    private EncoderAdapter encoder;
    private DecoderAdapter decoder;
    private byte[] seperateCharacter;
    private MessageBuilder messageBuilder;
    private boolean alreadyInitialized = false;
    // 改成单例
    private ProtocolContext() {}

    private static ProtocolContext i = new ProtocolContext();

    public static ProtocolContext instance() {
        return i;
    }

    public EncoderAdapter getEncoder() {
        if (!alreadyInitialized) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_02);
        }
        return encoder;
    }

    public DecoderAdapter getDecoder() {
        if (!alreadyInitialized) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_02);
        }
        return decoder;
    }

    public byte[] getSeperateCharacter() {
        if (!alreadyInitialized) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_02);
        }
        return seperateCharacter;
    }

    public MessageBuilder getMessageBuilder() {
        if (!alreadyInitialized) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_02);
        }
        return messageBuilder;
    }

    public void setEncoder(EncoderAdapter encoder) {
        this.encoder = encoder;
    }

    public void setDecoder(DecoderAdapter decoder) {
        this.decoder = decoder;
    }

    public void setSeperateCharacter(byte[] seperateCharacter) {
        this.seperateCharacter = seperateCharacter;
    }

    public void setMessageBuilder(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    public boolean isAlreadyInitialized() {
        return alreadyInitialized;
    }

    public void setAlreadyInitialized(boolean alreadyInitialized) {
        this.alreadyInitialized = alreadyInitialized;
    }
}
