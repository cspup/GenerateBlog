package com.csp;

import java.io.*;
import java.util.Properties;

/**
 * @author csp
 * @date 2022/1/6 19:09
 * @description Properties配置文件工具
 */
public class PropertiesUtils {
    Properties properties=new Properties();

    PropertiesUtils(String propertiesPath) throws IOException {
        FileReader reader = new FileReader(propertiesPath);
        properties.load(reader);
    }
    /**
     * 获取配置值
     * @param key 配置项
     * @return 配置值
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
