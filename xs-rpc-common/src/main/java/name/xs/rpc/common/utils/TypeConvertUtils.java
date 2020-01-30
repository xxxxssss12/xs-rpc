package name.xs.rpc.common.utils;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * create by xs
 * create time:2020-01-30 00:17:35
 */
public class TypeConvertUtils {

//    private static Set<Class<?>> baseTypeSet = new HashSet<>();
//    static {
//        baseTypeSet.add(java.lang.Integer.class);
//        baseTypeSet.add(java.lang.Byte.class);
//        baseTypeSet.add(java.lang.Long.class);
//        baseTypeSet.add(java.lang.Character.class);
//        baseTypeSet.add(java.lang.Boolean.class);
//        baseTypeSet.add(java.lang.Float.class);
//        baseTypeSet.add(java.lang.Double.class);
//        baseTypeSet.add(java.lang.Short.class);
//    }

    public static String Obj2Str(Object obj) {
        if (obj == null) {
            return null;
        }
//        if (obj.getClass().isArray()
//            || obj.getClass().isAssignableFrom(Collection.class)
//            || obj.getClass().isAssignableFrom(Map.class)) {
//            return JSON.toJSONString(obj);
//        }
//        if (obj.getClass().isPrimitive() || obj.getClass().equals(String.class) || obj.getClass().isAssignableFrom(Number.class) || obj.getClass().isEnum()) {
//            return obj.toString();
//        }
        return JSON.toJSONString(obj);
    }

    public static String[] objArr2StrArr(Object[] arguments) {
        if (arguments != null && arguments.length > 0) {
            String[] args = new String[arguments.length];
            for (int i=0; i<arguments.length; i++) {
                Object arg = arguments[i];
                if (arg != null) {
                    args[i] = JSON.toJSONString(arg);
                } else {
                    args[i] = null;
                }
            }
            return args;
        } else {
            return new String[0];
        }
    }

    public static Object[] strArr2objArr(String[] argumentStrArr, Class<?>[] argTypes) {
        if (argumentStrArr != null && argumentStrArr.length > 0) {
            Object[] args = new Object[argumentStrArr.length];
            for (int i=0; i<argumentStrArr.length; i++) {
                String argStr = argumentStrArr[i];
                if (argStr != null) {
                    args[i] = JSON.parseObject(argStr, argTypes[i]);
                } else {
                    args[i] = null;
                }
            }
            return args;
        } else {
            return new Object[0];
        }
    }

    public static String[] classArr2StrArr(Class<?>[] parameterTypes) {
        if (parameterTypes != null && parameterTypes.length != 0) {
            String[] paramTypes = new String[parameterTypes.length];
            for (int i=0; i<parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (parameterType != null) {
                    paramTypes[i] = parameterType.getName();
                }
            }
            return paramTypes;
        } else {
            return new String[0];
        }
    }

    public static Class<?>[] strArr2ClassArr(String[] parameterTypes) throws ClassNotFoundException {
        if (parameterTypes != null && parameterTypes.length != 0) {
            Class<?>[] typeArr = new Class[parameterTypes.length];
            for (int i=0; i<parameterTypes.length; i++) {
                String parameterTypeStr = parameterTypes[i];
                if (parameterTypeStr != null) {
                    typeArr[i] = getClassForName(parameterTypeStr);
                }
            }
            return typeArr;
        } else {
            return new Class[0];
        }
    }

    private static Class<?> getClassForName(String baseClassName) throws ClassNotFoundException {
        if ("int".equals(baseClassName)) {
            return int.class;
        }
        if ("byte".equals(baseClassName)) {
            return byte.class;
        }
        if ("short".equals(baseClassName)) {
            return short.class;
        }
        if ("long".equals(baseClassName)) {
            return long.class;
        }
        if ("boolean".equals(baseClassName)) {
            return boolean.class;
        }
        if ("char".equals(baseClassName)) {
            return char.class;
        }
        if ("float".equals(baseClassName)) {
            return float.class;
        }
        if ("double".equals(baseClassName)) {
            return double.class;
        }
        return Class.forName(baseClassName);
    }

    public static void main(String[] args) {
        String obj = "1";
        System.out.println(JSON.parseObject(obj, Integer.class));
    }

    public static String convert2pkgType(String baseClassName) {
        if ("int".equals(baseClassName)) {
            return "java.lang.Integer";
        }
        if ("byte".equals(baseClassName)) {
            return "java.lang.Byte";
        }
        if ("short".equals(baseClassName)) {
            return "java.lang.Short";
        }
        if ("long".equals(baseClassName)) {
            return "java.lang.Long";
        }
        if ("boolean".equals(baseClassName)) {
            return "java.lang.Boolean";
        }
        if ("char".equals(baseClassName)) {
            return "java.lang.Character";
        }
        if ("float".equals(baseClassName)) {
            return "java.lang.Float";
        }
        if ("double".equals(baseClassName)) {
            return "java.lang.Double";
        }
        return baseClassName;
    }
}
