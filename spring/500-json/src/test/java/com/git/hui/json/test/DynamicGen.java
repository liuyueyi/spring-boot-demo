package com.git.hui.json.test;


import com.itranswarp.compiler.JavaStringCompiler;
import org.junit.Test;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/8/20
 */
public class DynamicGen {
    public static Object run(String source, String... args) throws Exception {
        // 声明类名
        String className = "Main";
        String packageName = "com.git.hui";
        String prefix = String.format("package %s;", packageName);
        String fullName = String.format("%s.%s", packageName, className);

        // 编译器
        JavaStringCompiler compiler = new JavaStringCompiler();
        // 编译：compiler.compile("Main.java", source)
        Map<String, byte[]> results = compiler.compile(className + ".java", prefix + source);
        // 加载内存中byte到Class<?>对象
        Class<?> clazz = compiler.loadClass(fullName, results);
        // 创建实例
        Object instance = clazz.newInstance();
        Method mainMethod = clazz.getMethod("main", String[].class);
        // String[]数组时必须使用Object[]封装
        // 否则会报错：java.lang.IllegalArgumentException: wrong number of arguments
        return mainMethod.invoke(instance, new Object[]{args});
    }


    /**
     * 如果出现 java.lang.NoClassDefFoundError: com/sun/tools/javac/processing/JavacProcessingEnvironment
     * 解决办法是： file -> project structure -> modules -> dependencies -> 导入 jdk 目录下的 libs/tools.jar 即可
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 传入String类型的代码
        String source =
                "import java.util.Arrays;" +
                "public class Main {" +
                "   public static void main(String[] args) {" +
                "       System.out.println(Arrays.toString(args));" +
                "   }" +
                "}";
        run(source, "1", "2");
    }

    @Test
    public void testFormat() {
        double pi = 31415926;
        System.out.println(new DecimalFormat(",###").format(pi));
        System.out.println(new DecimalFormat("您的余额,###").format(pi));
    }
}
