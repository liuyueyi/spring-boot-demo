package com.git.hui.boot.web.dynamic;


import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by @author yihui in 14:27 19/3/26.
 */
public class DynamicObjLoader {
    private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s*");

    private static JavaCompiler compiler;
    private static DiagnosticCollector<JavaFileObject> collector;
    private static StandardJavaFileManager standardJavaFileManager;
    private static List<String> options;

    static {
        compiler = ToolProvider.getSystemJavaCompiler();
        standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
        options = new ArrayList<>(2);
        options.add("-target");
        options.add("1.8");
        options.add("-classpath");
        options.add(buildClassPath(
                "/Users/user/Project/GitHub/spring-boot-demo/spring-boot/202-web-params/BOOT-INF" + "/lib" + ""));
        //设定使用javaUtilZip，避免zipFileIndex泄漏
        options.add("-XDuseJavaUtilZip");

    }

    private DynamicObjLoader() {
    }

    private static String buildClassPath() {
        StringBuilder sb = new StringBuilder();
        URLClassLoader parentClassLoader = (URLClassLoader) DynamicObjLoader.class.getClassLoader();
        for (URL url : parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        return sb.toString();
    }

    private static String buildClassPath(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (path.endsWith("*")) {
                path = path.substring(0, path.length() - 1);
                File pathFile = new File(path);
                for (File file : pathFile.listFiles()) {
                    if (file.isFile() && file.getName().endsWith(".jar")) {
                        sb.append(path);
                        sb.append(file.getName());
                        sb.append(System.getProperty("path.separator"));
                    }
                }
            } else {
                sb.append(path);
                sb.append(System.getProperty("path.separator"));
            }
        }
        return sb.toString();
    }

    public static Object loadTask(String code) throws RuntimeException {
        Matcher matcher = CLASS_PATTERN.matcher(code);
        String cls;
        if (matcher.find()) {
            cls = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No such class name in " + code);
        }

        JavaFileObject javaFileObject = new DynamicObjObject(cls, code);

        DynamicObjManager javaFileManager = new DynamicObjManager(standardJavaFileManager);

        // 收集异常堆栈信息
        collector = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task =
                compiler.getTask(null, javaFileManager, collector, options, null, Arrays.asList(javaFileObject));
        Boolean result = task.call();
        if (!result) {
            throwTaskCompileError(collector.getDiagnostics());
        }

        try {
            return javaFileManager.loadClass(cls).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load2mem:\n " + code, e);
        }

    }


    /**
     * 编译失败，抛出对应的错误信息
     *
     * @param diagnostics
     * @throws RuntimeException
     */
    private static void throwTaskCompileError(List<Diagnostic<? extends JavaFileObject>> diagnostics)
            throws RuntimeException {
        StringBuilder compileMsg = new StringBuilder();
        for (Diagnostic<? extends JavaFileObject> d : diagnostics) {
            compileMsg.append(d.toString()).append("\n");
        }

        throw new RuntimeException(compileMsg.toString());
    }
}
