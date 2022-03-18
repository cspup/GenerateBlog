# GenerateBlog
markdown博客生成工具  
1. config.properties中配置想要输出的位置outDir，运行jar包，在outDir下生成目录  
2. 在outDir中的/md目录下放置你的markdown文件  
3. 运行jar包，/md下的文件生成html文件存放至/html目录，同时更新根目录的index.html文件  
markdown解析使用了[flexmark-java](https://github.com/vsch/flexmark-java) 
4. 模板文件存放在model文件夹下，可根据需要修改
