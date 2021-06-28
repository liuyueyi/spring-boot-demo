import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author yihui
 * @date 2021/4/30
 */
public class LoggerWriter {
    private static final String ARG = "${arg}";

    private static final String PARAM = "%s";

    private static final String PARAM_REGEX = "\\%s";

    private static final String ARG_REGEX = "\\$\\{arg\\}";

    private static final Pattern PHRASE_REGEX = Pattern.compile("\\$\\{[A-Za-z]+/[A-Za-z]+[/[A-Za-z]+]+(:.+){0,1}\\}");

    public static String write(String phrasePath, Object... args) {
        String ret = new String(phrasePath);
        try {
            if (phrasePath != null) {
                int argsIndex = 1;
                while (phrasePath.indexOf(LoggerWriter.PARAM) != -1) {
                    if (args == null || args.length < argsIndex) {
                        throw new RuntimeException("参数异常");
                    }
                    phrasePath = phrasePath.replaceFirst(LoggerWriter.PARAM_REGEX, "\"" + rebuild(String.valueOf(args[argsIndex - 1])) + "\"");
                    argsIndex++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
        return phrasePath;
    }

    /**
     * 重新构建
     *
     * @param content
     * @return
     */
    private static String rebuild(String content) {
        StringBuffer buf = new StringBuffer();
        char[] chs = content.toCharArray();
        for (char ch : chs) {
            if (ch == '$') {
                buf.append("\\$");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    @Test
    public void testParse() {
        String key = "${oc/orderBiz/create:%s,%s,%s}";
        String ans = write(key, "xiaomi", "!23456", "456");
        System.out.println(ans);
    }
}
