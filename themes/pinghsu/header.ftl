<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-transform"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="shortcut icon" href="${blogProperty("siteFavicon")}">
    <title>${blogProperty("siteTitle")}</title>
    <meta name="keywords" content="${blogProperty("siteKeywords")}" />
    <#--<?php $this->header('keywords=&generator=&template=&pingback=&xmlrpc=&wlw=&commentReply=&rss1=&rss2=&atom='); ?>-->
    <link href="//cdn.bootcss.com/highlight.js/9.10.0/styles/xcode.min.css" rel="stylesheet">
    <link href="/${themeInfo("themeFilePath")}/style.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<#--<?php if (array_key_exists('archive',unserialize($this->___fields()))): ?>bg-grey<?php elseif($this->is('archive')&&($this->options->colorBgPosts == 'defaultColor')): ?>bg-grey<?php elseif($this->is('archive')&&($this->options->colorBgPosts == 'customColor')): ?>bg-white<?php elseif(!$this->is('single')): ?>bg-grey<?php endif; ?>-->
<body class="bg-grey" gtools_scp_screen_capture_injected="true">
<!--[if lt IE 8]>
<div class="browsehappy" role="dialog">
    当前网页 <strong>不支持</strong> 你正在使用的浏览器. 为了正常的访问, 请 <a href="http://browsehappy.com/" target="_blank">升级你的浏览器</a>。
</div>
<![endif]-->
<#--随机颜色函数，返回css中定义好的颜色-->
<#function randomColor bright=true>
    <#if bright>
        <#switch random(0,5)>
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
    <#switch random(0,8)>
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
            <#--<?php if($this->options->logoUrl): ?>-->
            <#--<img src="<?php $this->options->logoUrl();?>" alt="<?php $this->options->title() ?>" />-->
            <#--<?php else : ?>-->
            <#--<?php $this->options->title() ?>-->
            ${blogProperty("siteTitle")}
            <#--<?php endif; ?>-->
        </a>
        <div class="navbar-menu">
            <#--<?php $this->widget('Widget_Contents_Page_List')->to($pages); ?>-->
            <#--<?php while($pages->next()): ?>-->
            <#list getPages()! as page>
                <a href="${page.page_url}">${page.page_name}</a>
            </#list>

            <#--<a<?php if($this->is('page', $pages->slug)): ?> class="current"<?php endif; ?> href="<?php $pages->permalink(); ?>"><?php $pages->title(); ?></a>-->
            <#--<?php endwhile; ?>-->

        </div>
        <#--<?php if($this->options->searchPage): ?>-->
        <#--<a href="<?php $this->options->searchPage(); ?>" class="navbar-search">-->
            <#--<span class="icon-search"></span>-->
        <#--</a>-->
        <#--<?php else: ?>-->
        <div class="navbar-search" onclick="">
            <span class="icon-search"></span>
            <form id="search" method="post" action="<?php $this->options->siteUrl(); ?>" role="search">
                <span class="search-box">
                    <input type="text" id="input" class="input" name="s" required="true" placeholder="Search..." maxlength="30" autocomplete="off">
                </span>
            </form>
        </div>
        <#--<?php endif;?>-->
        <div class="navbar-mobile-menu" onclick="">
            <span class="icon-menu cross"><span class="middle"></span></span>
            <ul>
                <#list getPages()! as page>
                    <li><a href="${page.page_url}">${page.page_name}</a></li>
                </#list>
                <#--<?php $this->widget('Widget_Contents_Page_List')->to($pages); ?>-->
                <#--<?php while($pages->next()): ?>-->

                <#--<li><a<?php if($this->is('page', $pages->slug)): ?> class="current"<?php endif; ?> href="<?php $pages->permalink(); ?>"><?php $pages->title(); ?></a></li>-->
                <#--<?php endwhile; ?>-->

            </ul>
        </div>
    </div>
</header>