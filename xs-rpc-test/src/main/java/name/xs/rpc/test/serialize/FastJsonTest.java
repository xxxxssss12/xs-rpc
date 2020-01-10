package name.xs.rpc.test.serialize;

import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @author 熊顺
 * @CreateTime 2020/1/10 16:51
 */
public class FastJsonTest {

    public static void main(String[] args) {
        TestBeanA beanA = createBeanA();
        String json = JSON.toJSONString(beanA);
        System.out.println("json -> str :" + json);
        beanA = JSON.parseObject(json, TestBeanA.class);
        List<TestBeanB> list = beanA.getTestBeanBList();
        list.forEach(o -> System.out.println(JSON.toJSONString(o)));
    }

    private static TestBeanA createBeanA() {
        TestBeanA beanA = new TestBeanA();
        for (int i=0; i<10; i++) {
            TestBeanB beanB = new TestBeanB();
            for (int j=0; j<3; j++) {
                beanB.getLista().add("test_" + i + "_" + j);
                TestBeanA beanA1 = new TestBeanA();
                beanA1.setTestBeanBList(Collections.singletonList(new TestBeanB()));
                beanA1.setTestStr("test_" + i + "_" + j);
                beanA1.setTestInteger((i+1) * (j+1));
                beanB.getListb().put(TestEnumA.values()[j], beanA1);
            }
            beanA.getTestBeanBList().add(beanB);
        }
        return beanA;
    }
}
