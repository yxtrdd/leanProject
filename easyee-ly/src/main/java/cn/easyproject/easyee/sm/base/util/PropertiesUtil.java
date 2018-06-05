package cn.easyproject.easyee.sm.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties;

    static {
        URL url = PropertiesUtil.class.getClassLoader().getResource("config.properties");
        String file = url.getFile();
        file = file.replaceAll("%20", " ");
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        URL url = PropertiesUtil.class.getClassLoader().getResource("config.properties");
        String file = url.getFile();
        file = file.replaceAll("%20", " ");
        properties.clear();
        try {
            properties.load(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return getProperty(name, null);
    }

    public static String getProperty(String name, String defaultValue) {
        String value = properties.getProperty(name, defaultValue);
        if (null != value && value.length() > 0)
            value = value.trim();
        else {
            value = defaultValue;
        }
        return value;
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

}
