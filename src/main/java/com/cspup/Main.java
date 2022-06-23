package com.cspup;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        long startTime =  System.currentTimeMillis();
        Main main = new Main();
        Properties properties = new Properties();

        String classpath = Objects.requireNonNull(main.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        File file = new File(classpath);
//        运行目录
        String runPath = String.valueOf(file.getParentFile());

        InputStream inputStream = new FileInputStream(runPath + File.separator + "config.properties");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        try {
            properties.load(inputStreamReader);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
//        模板目录
        String modelDir = runPath + File.separator + "model";
//        输出目录
        String outDir = properties.getProperty("outDir");

//        ICP备案号
        String ICP = properties.getProperty("ICP");


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

        if (ICP!=null&&!ICP.isEmpty()){
            CreatePage.updateICP(indexOutFile,indexOutFile,ICP);
        }

        long endTime =  System.currentTimeMillis();
        long usedTime = (endTime-startTime);

        System.out.println("USE TIME:"+usedTime+"ms");
    }

}
