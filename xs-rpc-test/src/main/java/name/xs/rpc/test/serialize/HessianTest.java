package name.xs.rpc.test.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.xs.rpc.common.constants.Constant;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * @author 熊顺
 * @CreateTime 2019/12/13 10:17
 */
public class HessianTest {
    private static Logger log = LoggerFactory.getLogger(Constant.LOGGER_NAME);
    /**
     * Hessian实现序列化
     * @param employee
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj){
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            // Hessian的序列化输出
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Hessian实现反序列化
     * @param employeeArray
     * @return
     */
    public static Object deserialize(byte[] employeeArray) {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(employeeArray);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String [] args) {
        diguiStackException(1, 50);

    }

    private static void diguiStackException(int i, int n) {
        if (i<n) {
            diguiStackException(i+1, n);
        } else {
            Exception obj = new Exception();
            // 序列化
            byte[] serialize = serialize(obj);
            Constant.LOG.info("serialize.length={}", serialize.length);
            // 反序列化
            obj = (Exception) deserialize(serialize);
            String s = getErrorInfoFromException(obj);
            log.info(s);
            Constant.LOG.info("error2Str.length={}", s.getBytes().length);
        }

    }

    public static String getErrorInfoFromException(Throwable e) {
        try (StringWriter sw = new StringWriter()){
            try (PrintWriter pw = new PrintWriter(sw)) {
                e.printStackTrace(pw);
                String s = sw.toString().replaceAll(System.getProperty("line.separator"), ",").replaceAll("\\t", "");
                return s;
            } catch (Exception e1) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e.printStackTrace();
        }
        return null;
    }
}
