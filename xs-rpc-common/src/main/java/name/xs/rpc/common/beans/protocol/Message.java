package name.xs.rpc.common.beans.protocol;

import java.io.Serializable;

/**
 * @author xs
 * create time:2019-07-09 16:07:06
 */
public interface Message extends Serializable {

    String getData();

    String getSessionId();
}
