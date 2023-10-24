//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.git.hui.id.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.serializer.IStandardJavaScriptSerializer;
import org.thymeleaf.util.ClassLoaderUtils;
import org.thymeleaf.util.DateUtils;
import org.unbescape.json.JsonEscape;
import org.unbescape.json.JsonEscapeLevel;
import org.unbescape.json.JsonEscapeType;

/**
 * 直接copy org.thymeleaf.standard.serializer.StandardJavaScriptSerializer
 * 在 454行，添加 this.mapper.registerModule(JacksonUtil.bigIntToStrsimpleModule());
 */
public final class MyStandardJavaScriptSerializer implements IStandardJavaScriptSerializer {
    private static final Logger logger = LoggerFactory.getLogger(MyStandardJavaScriptSerializer.class);
    private final IStandardJavaScriptSerializer delegate;

    private String computeJacksonPackageNameIfPresent() {
        try {
            Class<?> objectMapperClass = ObjectMapper.class;
            String objectMapperPackageName = objectMapperClass.getPackage().getName();
            return objectMapperPackageName.substring(0, objectMapperPackageName.length() - ".databind".length());
        } catch (Throwable var3) {
            return null;
        }
    }

    public MyStandardJavaScriptSerializer(boolean useJacksonIfAvailable) {
        IStandardJavaScriptSerializer newDelegate = null;
        String jacksonPrefix = useJacksonIfAvailable ? this.computeJacksonPackageNameIfPresent() : null;
        if (jacksonPrefix != null) {
            try {
                newDelegate = new JacksonStandardJavaScriptSerializer(jacksonPrefix);
            } catch (Exception var5) {
                this.handleErrorLoggingOnJacksonInitialization(var5);
            } catch (NoSuchMethodError var6) {
                this.handleErrorLoggingOnJacksonInitialization(var6);
            }
        }

        if (newDelegate == null) {
            newDelegate = new DefaultStandardJavaScriptSerializer();
        }

        this.delegate = (IStandardJavaScriptSerializer)newDelegate;
    }

    public void serializeValue(Object object, Writer writer) {
        this.delegate.serializeValue(object, writer);
    }

    private void handleErrorLoggingOnJacksonInitialization(Throwable e) {
        String warningMessage = "[THYMELEAF] Could not initialize Jackson-based serializer even if the Jackson library was detected to be present at the classpath. Please make sure you are adding the jackson-databind module to your classpath, and that version is >= 2.5.0. THYMELEAF INITIALIZATION WILL CONTINUE, but Jackson will not be used for JavaScript serialization.";
        if (logger.isDebugEnabled()) {
            logger.warn("[THYMELEAF] Could not initialize Jackson-based serializer even if the Jackson library was detected to be present at the classpath. Please make sure you are adding the jackson-databind module to your classpath, and that version is >= 2.5.0. THYMELEAF INITIALIZATION WILL CONTINUE, but Jackson will not be used for JavaScript serialization.", e);
        } else {
            logger.warn("[THYMELEAF] Could not initialize Jackson-based serializer even if the Jackson library was detected to be present at the classpath. Please make sure you are adding the jackson-databind module to your classpath, and that version is >= 2.5.0. THYMELEAF INITIALIZATION WILL CONTINUE, but Jackson will not be used for JavaScript serialization. Set the log to DEBUG to see a complete exception trace. Exception message is: " + e.getMessage());
        }

    }

    private static final class DefaultStandardJavaScriptSerializer implements IStandardJavaScriptSerializer {
        private DefaultStandardJavaScriptSerializer() {
        }

        public void serializeValue(Object object, Writer writer) {
            try {
                writeValue(writer, object);
            } catch (IOException var4) {
                throw new TemplateProcessingException("An exception was raised while trying to serialize object to JavaScript using the default serializer", var4);
            }
        }

        private static void writeValue(Writer writer, Object object) throws IOException {
            if (object == null) {
                writeNull(writer);
            } else if (object instanceof CharSequence) {
                writeString(writer, object.toString());
            } else if (object instanceof Character) {
                writeString(writer, object.toString());
            } else if (object instanceof Number) {
                writeNumber(writer, (Number)object);
            } else if (object instanceof Boolean) {
                writeBoolean(writer, (Boolean)object);
            } else if (object instanceof Date) {
                writeDate(writer, (Date)object);
            } else if (object instanceof Calendar) {
                writeDate(writer, ((Calendar)object).getTime());
            } else if (object.getClass().isArray()) {
                writeArray(writer, object);
            } else if (object instanceof Collection) {
                writeCollection(writer, (Collection)object);
            } else if (object instanceof Map) {
                writeMap(writer, (Map)object);
            } else if (object instanceof Enum) {
                writeEnum(writer, object);
            } else {
                writeObject(writer, object);
            }
        }

        private static void writeNull(Writer writer) throws IOException {
            writer.write("null");
        }

        private static void writeString(Writer writer, String str) throws IOException {
            writer.write(34);
            writer.write(JsonEscape.escapeJson(str, JsonEscapeType.SINGLE_ESCAPE_CHARS_DEFAULT_TO_UHEXA, JsonEscapeLevel.LEVEL_2_ALL_NON_ASCII_PLUS_BASIC_ESCAPE_SET));
            writer.write(34);
        }

        private static void writeNumber(Writer writer, Number number) throws IOException {
            writer.write(number.toString());
        }

        private static void writeBoolean(Writer writer, Boolean bool) throws IOException {
            writer.write(bool.toString());
        }

        private static void writeDate(Writer writer, Date date) throws IOException {
            writer.write(34);
            writer.write(DateUtils.formatISO(date));
            writer.write(34);
        }

        private static void writeArray(Writer writer, Object arrayObj) throws IOException {
            writer.write(91);
            boolean first;
            int var5;
            int var6;
            if (arrayObj instanceof Object[]) {
                Object[] array = (Object[])arrayObj;
                first = true;
                Object[] var4 = array;
                var5 = array.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    Object element = var4[var6];
                    if (first) {
                        first = false;
                    } else {
                        writer.write(44);
                    }

                    writeValue(writer, element);
                }
            } else if (arrayObj instanceof boolean[]) {
                boolean[] array = (boolean[])arrayObj;
                first = true;
                boolean[] var15 = array;
                var5 = array.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    boolean element = var15[var6];
                    if (first) {
                        first = false;
                    } else {
                        writer.write(44);
                    }

                    writeValue(writer, element);
                }
            } else {
                int element;
                if (arrayObj instanceof byte[]) {
                    byte[] array = (byte[])arrayObj;
                    first = true;
                    byte[] var17 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        element = var17[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, Byte.valueOf((byte)element));
                    }
                } else if (arrayObj instanceof short[]) {
                    short[] array = (short[])arrayObj;
                    first = true;
                    short[] var18 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        element = var18[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, Short.valueOf((short)element));
                    }
                } else if (arrayObj instanceof int[]) {
                    int[] array = (int[])arrayObj;
                    first = true;
                    int[] var19 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        element = var19[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, element);
                    }
                } else if (arrayObj instanceof long[]) {
                    long[] array = (long[])arrayObj;
                    first = true;
                    long[] var20 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        long element2 = var20[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, element2);
                    }
                } else if (arrayObj instanceof float[]) {
                    float[] array = (float[])arrayObj;
                    first = true;
                    float[] var21 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        float element2 = var21[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, element2);
                    }
                } else {
                    if (!(arrayObj instanceof double[])) {
                        throw new IllegalArgumentException("Cannot write value \"" + arrayObj + "\" of class " + arrayObj.getClass().getName() + " as an array");
                    }

                    double[] array = (double[])arrayObj;
                    first = true;
                    double[] var22 = array;
                    var5 = array.length;

                    for(var6 = 0; var6 < var5; ++var6) {
                        double element2 = var22[var6];
                        if (first) {
                            first = false;
                        } else {
                            writer.write(44);
                        }

                        writeValue(writer, element2);
                    }
                }
            }

            writer.write(93);
        }

        private static void writeCollection(Writer writer, Collection<?> collection) throws IOException {
            writer.write(91);
            boolean first = true;

            Object element;
            for(Iterator var3 = collection.iterator(); var3.hasNext(); writeValue(writer, element)) {
                element = var3.next();
                if (first) {
                    first = false;
                } else {
                    writer.write(44);
                }
            }

            writer.write(93);
        }

        private static void writeMap(Writer writer, Map<?, ?> map) throws IOException {
            writer.write(123);
            boolean first = true;

            Map.Entry entry;
            for(Iterator var3 = map.entrySet().iterator(); var3.hasNext(); writeKeyValue(writer, entry.getKey(), entry.getValue())) {
                entry = (Map.Entry)var3.next();
                if (first) {
                    first = false;
                } else {
                    writer.write(44);
                }
            }

            writer.write(125);
        }

        private static void writeKeyValue(Writer writer, Object key, Object value) throws IOException {
            writeValue(writer, key);
            writer.write(58);
            writeValue(writer, value);
        }

        private static void writeObject(Writer writer, Object object) throws IOException {
            try {
                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
                Map<String, Object> properties = new LinkedHashMap(descriptors.length + 1, 1.0F);
                PropertyDescriptor[] var4 = descriptors;
                int var5 = descriptors.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor descriptor = var4[var6];
                    Method readMethod = descriptor.getReadMethod();
                    if (readMethod != null) {
                        String name = descriptor.getName();
                        if (!"class".equals(name.toLowerCase())) {
                            Object value = readMethod.invoke(object);
                            properties.put(name, value);
                        }
                    }
                }

                writeMap(writer, properties);
            } catch (IllegalAccessException var11) {
                throw new IllegalArgumentException("Could not perform introspection on object of class " + object.getClass().getName(), var11);
            } catch (InvocationTargetException var12) {
                throw new IllegalArgumentException("Could not perform introspection on object of class " + object.getClass().getName(), var12);
            } catch (IntrospectionException var13) {
                throw new IllegalArgumentException("Could not perform introspection on object of class " + object.getClass().getName(), var13);
            }
        }

        private static void writeEnum(Writer writer, Object object) throws IOException {
            Enum<?> enumObject = (Enum)object;
            writeString(writer, enumObject.toString());
        }
    }

    private static final class JacksonThymeleafCharacterEscapes extends CharacterEscapes {
        private static final int[] CHARACTER_ESCAPES = CharacterEscapes.standardAsciiEscapesForJSON();
        private static final SerializableString SLASH_ESCAPE;
        private static final SerializableString AMPERSAND_ESCAPE;

        JacksonThymeleafCharacterEscapes() {
        }

        public int[] getEscapeCodesForAscii() {
            return CHARACTER_ESCAPES;
        }

        public SerializableString getEscapeSequence(int ch) {
            if (ch == 47) {
                return SLASH_ESCAPE;
            } else {
                return ch == 38 ? AMPERSAND_ESCAPE : null;
            }
        }

        static {
            CHARACTER_ESCAPES[47] = -2;
            CHARACTER_ESCAPES[38] = -2;
            SLASH_ESCAPE = new SerializedString("\\/");
            AMPERSAND_ESCAPE = new SerializedString("\\u0026");
        }
    }

    private static final class JacksonThymeleafISO8601DateFormat extends DateFormat {
        private static final long serialVersionUID = 1354081220093875129L;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");

        JacksonThymeleafISO8601DateFormat() {
            this.setCalendar(this.dateFormat.getCalendar());
            this.setNumberFormat(this.dateFormat.getNumberFormat());
        }

        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
            StringBuffer formatted = this.dateFormat.format(date, toAppendTo, fieldPosition);
            formatted.insert(26, ':');
            return formatted;
        }

        public Date parse(String source, ParsePosition pos) {
            throw new UnsupportedOperationException("JacksonThymeleafISO8601DateFormat should never be asked for a 'parse' operation");
        }

        public Object clone() {
            JacksonThymeleafISO8601DateFormat other = (JacksonThymeleafISO8601DateFormat)super.clone();
            other.dateFormat = (SimpleDateFormat)this.dateFormat.clone();
            return other;
        }
    }

    private static final class JacksonStandardJavaScriptSerializer implements IStandardJavaScriptSerializer {
        private final ObjectMapper mapper = new ObjectMapper();

        JacksonStandardJavaScriptSerializer(String jacksonPrefix) {
            this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            this.mapper.disable(new JsonGenerator.Feature[]{Feature.AUTO_CLOSE_TARGET});
            this.mapper.enable(new JsonGenerator.Feature[]{Feature.ESCAPE_NON_ASCII});
            this.mapper.getFactory().setCharacterEscapes(new JacksonThymeleafCharacterEscapes());
            this.mapper.setDateFormat(new JacksonThymeleafISO8601DateFormat());
            Class<?> javaTimeModuleClass = ClassLoaderUtils.findClass(jacksonPrefix + ".datatype.jsr310.JavaTimeModule");
            if (javaTimeModuleClass != null) {
                try {
                    this.mapper.registerModule((Module)javaTimeModuleClass.newInstance());
                    this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                } catch (InstantiationException var4) {
                    throw new ConfigurationException("Exception while trying to initialize JSR310 support for Jackson", var4);
                } catch (IllegalAccessException var5) {
                    throw new ConfigurationException("Exception while trying to initialize JSR310 support for Jackson", var5);
                }
            }
            this.mapper.registerModule(JacksonUtil.bigIntToStrsimpleModule());
        }

        public void serializeValue(Object object, Writer writer) {
            try {
                this.mapper.writeValue(writer, object);
            } catch (IOException var4) {
                throw new TemplateProcessingException("An exception was raised while trying to serialize object to JavaScript using Jackson", var4);
            }
        }
    }
}
