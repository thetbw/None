# **None** 
**a java blog**

### 简介：

None 是我为了练习而制作的一款博客系统，因为想不到叫什么，所以，就叫None吧。因为本人技术不精，目前可能会有一点小问题，日后会慢慢修复

**[演示](http://blog.thetbw.xyz)**

前端的后台管理使用了react开发

**[前端后台源码](https://github.com/thetbw/None-admin)**


### 安装&&使用:

[None安装教程](http://blog.thetbw.xyz/article/1) (可能已经更改)

前提:

* java 
* mysql

在mysql中创建一个数据库用来存放数据库文件，表系统会自动创建 **特别注意数据库 charset 一定要 utf8mb4**

下载release 中的jar
```
java -jar 文件名
```
linux 可以使用 `nohup`和`&`命令来让服务保持在后台

初次使用None应该会把jar包内的资源解压到所在文件夹中，你需要按照你的需求编辑`application.properties`文件，尤其是其中的数据库相关配置

然后再次运行程序，访问`域名/install`就可以完成安装

### 功能实现：

* 基本的文章编写，支持 markdown语法，支持密码访问，自定义封面
* 分类，标签功能
* 基本评论功能
* 多主题，主题切换，使用 FreeMarker 渲染页面，支持前后端分离
* 自定义页面，页面排序

### 目前已知问题：

* 后台同时删除多条评论可能造成死锁，导致删除失败，不过不会破坏数据，重新删除即可
* 用于验证用户身份的cookie字段可能会解密失败，导致需要重新登陆（服务器重启后出现，可能是重启导致的）
* simple主题不能回复别人评论
* pinghsu js加载有问题，有时会不能查看子评论，需要刷新
* pinghsu 分享按钮目前只是摆设

### 准备增加的功能:

* rss
* github 登陆
* 各种事件的邮件提醒
* 插件系统 ？？


### 其他：

* 主题开发 (文章编写中)
* api文档 (编写中)
### Licence:

[MIT Licence](./LICENSE)
