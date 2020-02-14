package name.xs.rpc.test.container;

/**
 * @author xs
 * create time:2019-11-29 15:24:38
 */
public class ContainerMain {

    public static void main(String[] args) {
        TestContainer container = new TestContainer();
        container.start();
        container.stop();
    }
}
