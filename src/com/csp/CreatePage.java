package com.csp;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author csp
 * @date 2021/12/19 15:30
 * @description 创建页面
 */
public class CreatePage {


    public void create(String fileName){

        String content = FileUtils.read(fileName);
    }

    public void createHtml(){

    }

    /**
     * 更新首页
     * @param modelFile 模板文件
     * @param markDownDirectory markdown文件夹路径
     * @param target 目标文件
     * @param basePath 目标库路径
     */
    public static void updateIndexHtml(String modelFile,String markDownDirectory,String target,String basePath){
        String html =  FileUtils.read(basePath+modelFile);
        StringBuilder contentStringBuilder = new StringBuilder();

        List<File> fileList = new ArrayList<>();
        FileUtils.getFileList(basePath+markDownDirectory,fileList);
        for (File file:fileList){
            String fileName =  file.getName();
            fileName = fileName.substring(0,fileName.lastIndexOf("."));
            String filePath = file.getPath().replaceAll("\\\\","/");
            String relativePath = filePath.replace(basePath,"");
            Date date = new Date(file.lastModified());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String lastUpdateTime = dateFormat.format(date);
            String li = "<li><a href=\"./blog.html?path="+relativePath+"\">"+fileName+"--"+lastUpdateTime+"</a></li>";
            contentStringBuilder.append(li).append(System.lineSeparator());

            System.out.println(relativePath);
        }

        String  newHtml = html.replace("{{blog-list}}",contentStringBuilder.toString());

        FileUtils.write(newHtml,basePath+target,false);
    }


}
