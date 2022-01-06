package com.csp;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {

        Properties properties=new Properties();
        String propertiesPath = "config.properties";
        FileReader reader = new FileReader(propertiesPath);
        properties.load(reader);


        String modelFile  = properties.getProperty("modelFile");
        String markDownDirectory = properties.getProperty("markDownDirectory");
        String targetFile = properties.getProperty("targetFile");
        String basePath = properties.getProperty("basePath");

        CreatePage.updateIndexHtml(modelFile,markDownDirectory,targetFile,basePath);

    }
}
