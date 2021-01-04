package com.git.hui.boot.multi.datasource.dynamic;

/**
 * @author yihui
 * @date 20/12/27
 */
public class DataSourceType {
    // 使用ThreadLocal保证线程安全
    private static final ThreadLocal<String> TYPE = new ThreadLocal<String>();

    // 往当前线程里设置数据源类型
    public static void setDataBaseType(String dataBase) {
        if (dataBase == null) {
            throw new NullPointerException();
        }
        System.err.println("[将当前数据源改为]：" + dataBase);
        TYPE.set(dataBase);
    }

    // 获取数据源类型
    public static String getDataBaseType() {
        String database = TYPE.get();
        System.err.println("[获取当前数据源的类型为]：" + database);
        return database;
    }

    // 清空数据类型
    public static void clearDataBaseType() {
        TYPE.remove();
    }
}
