package name.xs.rpc.common.beans;

/**
 * create by xs
 * create time:2019-12-08 22:36:50
 */
public interface Result {
    /**
     * Get invoke result.
     *
     * @return result. if no result return null.
     */
    Object getValue();

    void setValue(Object value);

    /**
     * Get exception.
     *
     * @return exception. if no exception return null.
     */
    XsRpcExceptionSerialize getException();

    void setException(XsRpcExceptionSerialize t);

    /**
     * Has exception.
     *
     * @return has exception.
     */
    boolean hasException();
}
