<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-transform"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="shortcut icon" href="${_property("siteFavicon")}">
    <title><#if article??>
            ${article.article_title}
        <#else >
            ${_property("siteTitle")}
        </#if></title>
    <meta name="keywords" content="${_property("siteKeywords")}" />
    <link href="//cdn.bootcss.com/highlight.js/9.10.0/styles/xcode.min.css" rel="stylesheet">
    <link href="/${_theme("themeFilePath")}/style.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="bg-grey" gtools_scp_screen_capture_injected="true">
${_property("siteHeader")!}
<!--[if lt IE 8]>
<div class="browsehappy" role="dialog">
    当前网页 <strong>不支持</strong> 你正在使用的浏览器. 为了正常的访问, 请 <a href="http://browsehappy.com/" target="_blank">升级你的浏览器</a>。
</div>
<![endif]-->
<#--随机颜色函数，返回css中定义好的颜色-->
<#function randomColor bright=true>
    <#if bright>
        <#switch _u_random(0,5)>
            <#case 0>
                <#return "bg-blue">
            <#case 1>
                <#return "bg-purple">
            <#case 2>
                <#return "bg-green">
            <#case 3>
                <#return "bg-yellow">
            <#case 4>
                <#return "bg-red">
            <#case 5>
                <#return "bg-orange">
            <#default >
            <#return "bg-orange">
        </#switch>
    <#else >
    <#switch _u_random(0,8)>
        <#case 0>
        <#return "bg-white">
        <#case 1>
        <#return "bg-grey">
        <#case 2>
        <#return "bg-deepgrey">
        <#case 3>
        <#return "bg-blue">
        <#case 4>
        <#return "bg-purple">
        <#case 5>
        <#return "bg-green">
        <#case 6>
        <#return "bg-yellow">
        <#case 7>
        <#return "bg-red">
        <#case 8>
        <#return "bg-orange">
        <#default >
        <#return "bg-grey">
    </#switch>
    </#if>

</#function>

<header id="header" class="header bg-white">
    <div class="navbar-container">
        <a href="/" class="navbar-logo">
            ${_property("siteTitle")}
        </a>
        <div class="navbar-menu">
            <#list _pages()! as page>
                <a href="${page.page_url}">${page.page_name}</a>
            </#list>
        </div>
        <div class="navbar-search" onclick="">
            <span class="icon-search"></span>
            <form id="search" method="get" action="/" role="search">
                <span class="search-box">
                    <input type="text" id="input" class="input" name="s" required="true" placeholder="Search..." maxlength="30" autocomplete="off">
                </span>
            </form>
        </div>
        <div class="navbar-mobile-menu" onclick="">
            <span class="icon-menu cross"><span class="middle"></span></span>
            <ul>
                <#list _pages()! as page>
                    <li><a href="${page.page_url}">${page.page_name}</a></li>
                </#list>
            </ul>
        </div>
    </div>
</header>