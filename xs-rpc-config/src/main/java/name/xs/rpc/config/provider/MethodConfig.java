package name.xs.rpc.config.provider;

import java.util.List;

/**
 * create by xs, 2019-06-30 17:38
 */
public class MethodConfig {

    private String name;    // 方法名

    private Class<?> returnClass;

    private List<Class<?>> paramsTypeList;

    private List<String> paramsNameList;

    public Class<?> getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class<?> returnClass) {
        this.returnClass = returnClass;
    }

    public List<Class<?>> getParamsTypeList() {
        return paramsTypeList;
    }

    public void setParamsTypeList(List<Class<?>> paramsTypeList) {
        this.paramsTypeList = paramsTypeList;
    }

    public List<String> getParamsNameList() {
        return paramsNameList;
    }

    public void setParamsNameList(List<String> paramsNameList) {
        this.paramsNameList = paramsNameList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
