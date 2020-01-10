package name.xs.rpc.test.proxy;

/**
 * create by xs
 * create time:2019-07-15 16:23:43
 */
public class ProxyFactoryTest {
    public static void main(String[] args) {
        FirstService enhanceObj = (FirstService) ProxyFactory.getProxy(FirstServiceImpl.class);
        System.out.println(enhanceObj.add(1,2));
    }
}
