package name.xs.rpc.test.serialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xs
 * @CreateTime 2020/1/10 16:49
 */
public class TestBeanA implements Serializable {

    private List<TestBeanB> testBeanBList = new ArrayList<>();
    private String testStr = "";

    private Integer testInteger = 0;

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public List<TestBeanB> getTestBeanBList() {
        return testBeanBList;
    }

    public void setTestBeanBList(List<TestBeanB> testBeanBList) {
        this.testBeanBList = testBeanBList;
    }
}
