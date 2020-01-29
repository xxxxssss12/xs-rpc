package name.xs.rpc.test.proxy;

/**
 * create by xs
 * create time:2019-07-15 16:23:43
 */
public class ProxyFactoryTest {
    public static void main(String[] args) throws Throwable {
//        FirstService service = FirstService.class.getConstructor().newInstance();
//        FirstService enhanceObj = ProxyFactory.getLocalProxy(FirstServiceImpl.class);
//        System.out.println(enhanceObj.add(1,2));

        FirstService newService = ProxyFactory.getRemoteProxy(FirstService.class);
        System.out.println(newService.add(1, 2));
    }
}
