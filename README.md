# 捶捶自己博客的介绍

> 基于李仁密老师的视频开发并自己新增了一些内容

## 使用技术

前端页面用了semantic UI 进行搭建，thymeleaf模板进行解析
- 后台博客发布页面引入了`commonmark`插件实现`markdown语法`
- 引入插件博客中有介绍，或在`static/lib`中自行寻找

后端博客采用了: Maven + springboot + springJPA + springMVC + mysql

配置文件为: application.yaml

## 新增内容

前端博客美化效果：雪花效果，动态背景线条效果，点击显示文字效果

- 雪花效果相关配置：
    - resources/static/js/snow.js文件内，有详细注释说明 可配置雪花颜色，大小，密集程度

- 动态背景线条相关配置：
    - comments.html中 可配置颜色，透明度

- 点击显示文字效果相关配置：
    - resources/static/js/click_show_text.js文件内，可自定义随机显示的文字

- 引入网易云音乐插件，固定在左下角
    - 底部footer网站运行计时，在_fragments.html文件createtime( ) JS方法内自行替换建站时间

