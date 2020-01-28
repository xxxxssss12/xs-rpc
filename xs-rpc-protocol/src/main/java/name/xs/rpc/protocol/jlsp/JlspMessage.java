package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.protocol.Message;

/**
 * create by xs, 2019-07-14 22:13
 */
public class JlspMessage implements Message {
    private String data;
    private String sessionId;
    public JlspMessage(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
