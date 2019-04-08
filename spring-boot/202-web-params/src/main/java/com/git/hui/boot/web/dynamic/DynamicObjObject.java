package com.git.hui.boot.web.dynamic;


import org.springframework.util.StringUtils;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by @author yihui in 14:42 19/3/26.
 */
public class DynamicObjObject extends SimpleJavaFileObject {
    private static final String DEFAULT_PACKAGE =
            StringUtils.replace("net.finbtc.doraemon.", ".", java.io.File.separator);
    private String source;
    private ByteArrayOutputStream outPutStream;

    public DynamicObjObject(String name, String source) {
        super(URI.create("String:///" + DEFAULT_PACKAGE + name + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = source;
    }

    public DynamicObjObject(String name, Kind kind) {
        super(URI.create("String:///" + DEFAULT_PACKAGE + name + kind.extension), kind);
        source = null;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        return source;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        outPutStream = new ByteArrayOutputStream();
        return outPutStream;
    }

    public byte[] getCompiledBytes() {
        return outPutStream.toByteArray();
    }
}