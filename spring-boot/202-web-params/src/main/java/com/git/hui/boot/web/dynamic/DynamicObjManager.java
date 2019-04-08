package com.git.hui.boot.web.dynamic;

import com.git.hui.boot.web.SpringUtil;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

/**
 * Created by @author yihui in 14:42 19/3/26.
 */
public class DynamicObjManager extends ForwardingJavaFileManager<JavaFileManager> {
    private DynamicTaskClassLoader classLoader;
    private DynamicObjObject dynamicTaskObject;

    private String taskName;

    public DynamicObjManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if (classLoader == null) {
            synchronized (this) {
                if (classLoader == null) {
                    classLoader = new DynamicTaskClassLoader();
                }
            }
        }
        return classLoader.loadClass(name);
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        ClassLoader launchedURLClassLoader = SpringUtil.getBean("helloRest").getClass().getClassLoader();
        EngineClassLoader engineClassLoader = new EngineClassLoader(launchedURLClassLoader);
        return engineClassLoader;
    }

    private class EngineClassLoader extends ClassLoader {
        public EngineClassLoader(ClassLoader parent) {
            super(parent);
        }
    }

    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind)
            throws IOException {
        if (dynamicTaskObject == null) {
            super.getJavaFileForInput(location, className, kind);
        }
        return dynamicTaskObject;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String qualifiedClassName, JavaFileObject.Kind kind,
            FileObject sibling) throws IOException {
        dynamicTaskObject = new DynamicObjObject(qualifiedClassName, kind);
        taskName = qualifiedClassName;
        return dynamicTaskObject;
    }

    public class DynamicTaskClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (taskName.equals(name) || taskName.endsWith("." + name)) {
                JavaFileObject fileObject = dynamicTaskObject;
                if (fileObject != null) {
                    byte[] bytes = ((DynamicObjObject) fileObject).getCompiledBytes();
                    return defineClass(taskName, bytes, 0, bytes.length);
                }
            }

            try {
                return this.getClass().getClassLoader().loadClass(name);
                // return ClassLoader.getSystemClassLoader().loadClass(name);
            } catch (Exception e) {
                return super.findClass(name);
            }
        }
    }
}
