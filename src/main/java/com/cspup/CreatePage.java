package com.cspup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author csp
 * @date 2021/12/19 15:30
 * @description 创建页面
 */
public class CreatePage {


    /**
     * 生成静态页面
     *
     * @param modelFile         模板文件
     * @param markDownDirectory markdown文件存放路径
     * @param htmlPath          html存放地
     */
    public static List<String> createHtml(String modelFile, String markDownDirectory, String htmlPath) throws IOException {
        FileUtils.copyDirectory(new File(markDownDirectory), new File(htmlPath));

        markDownDirectory = htmlPath;

        List<String> blogs = new ArrayList<>();

        String model = FileUtil.read(modelFile);
        Md2Html md2Html = new Md2Html();

        List<File> fileList = new ArrayList<>();
        FileUtil.getFileList(markDownDirectory, fileList);

        // 根据文件路径排序（由于md文件按日期分类其实是按日期排序）
        fileList.sort(Comparator.comparing(File::getPath).reversed());

        for (File file : fileList) {
            String suffix = FileUtil.getSuffix(file);
            // 排除非md文件
            if (!suffix.equals(".md")) {
                continue;
            }
            String mdContent = FileUtil.read(file);
            String html = md2Html.toHtml(mdContent);
            html = model.replace("{md-view}", html);

            String newName = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + ".html";
            FileUtil.write(html, newName, false);

            blogs.add(newName);
            System.out.println(newName);
        }
        System.out.println("Total:"+blogs.size());
        return blogs;
    }

    /**
     * 更新首页
     * @param outDir 输出根目录
     * @param indexModel 模板文件地址
     * @param target 输出地址
     * @param blogs 生成的blog列表
     */
    public static void updateIndexHtml(String outDir, String indexModel, String target, List<String> blogs) {
        List<String> list = new ArrayList<>();
        for (String blog : blogs) {
            blog = blog.replaceAll("\\\\", "/");
            String relativePath = blog.replace(outDir, "");
            File file = new File(blog);
            String fileName = file.getName();
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            String li = "<li><a href=\"" + relativePath + "\">" + fileName + "</a></li>";
            list.add(li);
        }

        String html = FileUtil.read(indexModel);
        StringBuilder contentStringBuilder = new StringBuilder();
        for (String li : list) {
            contentStringBuilder.append(li).append(System.lineSeparator());
        }
        String newHtml = html.replace("{{blog-list}}", contentStringBuilder.toString());

        FileUtil.write(newHtml, target, false);
    }


}
