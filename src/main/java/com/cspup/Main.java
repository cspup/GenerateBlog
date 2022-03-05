package com.cspup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        Properties properties = new Properties();

        String classpath = Objects.requireNonNull(main.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        File file = new File(classpath);
//        运行目录
        String runPath = String.valueOf(file.getParentFile());

        InputStream inputStream = new FileInputStream(runPath + File.separator + "config.properties");

        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
//        模板目录
        String modelDir = runPath + File.separator + "model";
//        输出目录
        String outDir = properties.getProperty("outDir");

        System.out.println("Out Dir:"+outDir);

        String indexModel = modelDir + "/model/index.html";
        String markDownDirectory = outDir + "/md";
        String indexOutFile = outDir + "/index.html";
        String blogHtmlDir = outDir + "/html";
        String blogModel = modelDir + "/model/blog.html";

        if (!new File(outDir + "/md").exists()){
            FileUtils.copyDirectory(new File(modelDir + "/md"), new File(outDir + "/md"));
        }


        FileUtils.copyDirectory(new File(modelDir + "/css"), new File(outDir + "/css"));
        FileUtils.copyDirectory(new File(modelDir + "/js"), new File(outDir + "/js"));

        List<String> blogs = CreatePage.createHtml(blogModel, markDownDirectory, blogHtmlDir);
        CreatePage.updateIndexHtml(outDir, indexModel, indexOutFile, blogs);


    }

}
