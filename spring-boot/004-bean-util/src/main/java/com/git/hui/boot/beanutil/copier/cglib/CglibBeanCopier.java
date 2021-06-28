package com.git.hui.boot.beanutil.copier.cglib;

import com.git.hui.boot.beanutil.copier.util.StrUtil;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Type;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.*;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

/**
 * cglib bean copier 扩展，支持驼峰与下划线的转换
 *
 * @author yihui
 * @date 2021/4/8
 */
public abstract class CglibBeanCopier {
    private static final CglibBeanCopier.BeanCopierKey KEY_FACTORY = (CglibBeanCopier.BeanCopierKey) KeyFactory.create(CglibBeanCopier.BeanCopierKey.class);
    private static final Type CONVERTER = TypeUtils.parseType("org.springframework.cglib.core.Converter");
    private static final Type BEAN_COPIER = TypeUtils.parseType("org.springframework.cglib.beans.BeanCopier");
    private static final Signature COPY;
    private static final Signature CONVERT;

    public CglibBeanCopier() {
    }

    public static BeanCopier create(Class source, Class target, boolean useConverter) {
        CglibBeanCopier.Generator gen = new CglibBeanCopier.Generator();
        gen.setSource(source);
        gen.setTarget(target);
        gen.setUseConverter(useConverter);
        return gen.create();
    }

    public abstract void copy(Object var1, Object var2, Converter var3);

    static {
        COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
        CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");
    }

    public static class Generator extends AbstractClassGenerator {
        private static final Source SOURCE = new Source(BeanCopier.class.getName());
        private Class source;
        private Class target;
        private boolean useConverter;

        public Generator() {
            super(SOURCE);
        }

        public void setSource(Class source) {
            if (!Modifier.isPublic(source.getModifiers())) {
                this.setNamePrefix(source.getName());
            }

            this.source = source;
        }

        public void setTarget(Class target) {
            if (!Modifier.isPublic(target.getModifiers())) {
                this.setNamePrefix(target.getName());
            }

            this.target = target;
        }

        public void setUseConverter(boolean useConverter) {
            this.useConverter = useConverter;
        }

        @Override
        protected ClassLoader getDefaultClassLoader() {
            return this.source.getClassLoader();
        }

        @Override
        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(this.source);
        }

        public BeanCopier create() {
            Object key = CglibBeanCopier.KEY_FACTORY.newInstance(this.source.getName(), this.target.getName(), this.useConverter);
            return (BeanCopier) super.create(key);
        }

        @Override
        public void generateClass(ClassVisitor v) {
            Type sourceType = Type.getType(this.source);
            Type targetType = Type.getType(this.target);
            ClassEmitter ce = new ClassEmitter(v);
            ce.begin_class(52, 1, this.getClassName(), CglibBeanCopier.BEAN_COPIER, (Type[]) null, "<generated>");
            EmitUtils.null_constructor(ce);
            CodeEmitter e = ce.begin_method(1, CglibBeanCopier.COPY, (Type[]) null);

            PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(this.target);
            Map<String, PropertyDescriptor> names = this.buildGetterNameMapper(this.source);

            Local targetLocal = e.make_local();
            Local sourceLocal = e.make_local();
            if (this.useConverter) {
                e.load_arg(1);
                e.checkcast(targetType);
                e.store_local(targetLocal);
                e.load_arg(0);
                e.checkcast(sourceType);
                e.store_local(sourceLocal);
            } else {
                e.load_arg(1);
                e.checkcast(targetType);
                e.load_arg(0);
                e.checkcast(sourceType);
            }

            for (int i = 0; i < setters.length; ++i) {
                PropertyDescriptor setter = setters[i];
                PropertyDescriptor getter = this.loadSourceGetter(names, setter);
                if (getter == null) {
                    continue;
                }

                MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
                MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());
                if (this.useConverter) {
                    Type setterType = write.getSignature().getArgumentTypes()[0];
                    e.load_local(targetLocal);
                    e.load_arg(2);
                    e.load_local(sourceLocal);
                    e.invoke(read);
                    e.box(read.getSignature().getReturnType());
                    EmitUtils.load_class(e, setterType);
                    e.push(write.getSignature().getName());
                    e.invoke_interface(CglibBeanCopier.CONVERTER, CglibBeanCopier.CONVERT);
                    e.unbox_or_zero(setterType);
                    e.invoke(write);
                } else if (compatible(getter, setter)) {
                    e.dup2();
                    e.invoke(read);
                    e.invoke(write);
                }
            }

            e.return_value();
            e.end_method();
            ce.end_class();
        }

        private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
            return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
        }

        @Override
        protected Object firstInstance(Class type) {
            return ReflectUtils.newInstance(type);
        }

        @Override
        protected Object nextInstance(Object instance) {
            return instance;
        }

        /**
         * 获取目标的getter方法，支持下划线与驼峰
         *
         * @param source
         * @return
         */
        public Map<String, PropertyDescriptor> buildGetterNameMapper(Class source) {
            PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
            Map<String, PropertyDescriptor> names = new HashMap<>(getters.length);
            for (int i = 0; i < getters.length; ++i) {
                String name = getters[i].getName();
                String camelName = StrUtil.toCamelCase(name);
                names.put(name, getters[i]);
                if (!name.equalsIgnoreCase(camelName)) {
                    // 支持下划线转驼峰
                    names.put(camelName, getters[i]);
                }
            }
            return names;
        }

        /**
         * 根据target的setter方法，找到source的getter方法，支持下划线与驼峰的转换
         *
         * @param names
         * @param setter
         * @return
         */
        public PropertyDescriptor loadSourceGetter(Map<String, PropertyDescriptor> names, PropertyDescriptor setter) {
            String setterName = setter.getName();
            return names.getOrDefault(setterName, names.get(StrUtil.toCamelCase(setterName)));
        }
    }

    interface BeanCopierKey {
        Object newInstance(String var1, String var2, boolean var3);
    }
}
