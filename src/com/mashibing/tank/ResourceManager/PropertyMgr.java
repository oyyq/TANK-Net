package com.mashibing.tank.ResourceManager;//管理配置文件的类

import java.io.IOException;
import java.util.Properties;

/*
public class ResourceManager.PropertyMgr {
    static Properties props = new Properties();
    static {
        try {
            //将配置文件中的数据以流的方式读入
            props.load(ResourceManager.PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object get(String key){
        if (props == null) return null;
        return props.get(key);
    }
    //int getInt(key)
    //String getString(key)

}
*/

/**
 * 单例模式PropertyMgr
 */
public class PropertyMgr {
    //静态成员变量不是在JVM加载外部类到内存时就加载的，而是在第一次访问时加载，static代码块也是
    private PropertyMgr(){}      //私有构造器
    static Properties props = new Properties();
    static {
        try {
            //将配置文件中的数据以流的方式读入
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PropertyMgrHolder{
        private final static PropertyMgr INSTANCE = new PropertyMgr();
    }
    public static PropertyMgr getInstance(){
        return PropertyMgrHolder.INSTANCE;
    }

    public static Object get(String key){
        if (props == null) return null;
        return props.get(key);
    }
}