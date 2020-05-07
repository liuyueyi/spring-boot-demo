package com.git.hui.boot.dynamic.config.parser;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by @author yihui in 17:12 20/4/22.
 */
public enum MetaParser implements IMetaParser {
    STRING_PARSER {
        @Override
        public String parse(String val) {
            return val;
        }
    },

    SHORT_PARSER {
        @Override
        public Short parse(String val) {
            return Short.valueOf(val);
        }
    },

    INT_PARSER {
        @Override
        public Integer parse(String val) {
            return Integer.valueOf(val);
        }
    },

    LONG_PARSER {
        @Override
        public Long parse(String val) {
            return Long.valueOf(val);
        }
    },

    FLOAT_PARSER {
        @Override
        public Object parse(String val) {
            return null;
        }
    },

    DOUBLE_PARSER {
        @Override
        public Object parse(String val) {
            return Double.valueOf(val);
        }
    },

    BYTE_PARSER {
        @Override
        public Byte parse(String val) {
            if (val == null) {
                return null;
            }
            return Byte.valueOf(val);
        }
    },

    CHARACTER_PARSER {
        @Override
        public Character parse(String val) {
            if (val == null) {
                return null;
            }
            return val.charAt(0);
        }
    },

    BOOLEAN_PARSER {
        @Override
        public Boolean parse(String val) {
            return Boolean.valueOf(val);
        }
    },

    JSON_PARSER {
        @Override
        public JSONObject parse(String val) {
            return JSONObject.parseObject(val);
        }
    };

}
