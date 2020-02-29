<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <title>${_property("siteTitle")}</title>
    <link rel="icon shortcut" type="image/x-icon" href="${_property("siteFavicon")}">
    <meta name="description" content="${_property("siteDescription")}">
    <meta name="keywords" content="${_property("siteDescription")}">
    <meta name="theme" content="${_theme("themeName")}">
    <link type="text/css" rel="stylesheet" href="/${_theme("themeFilePath")}/css/style.css"/>
    <script src="/${_theme("themeFilePath")}/js/jquery-3.4.1.min.js"></script>
</head>
<body>
<header id="header">
    <div class="page-title">
        <h1><a href="/" style="text-decoration: none;color: black">${_property("siteTitle")}</a></h1>
        <p class="description"><i >${_property("siteDescription")}</i></p>
    </div>
    <p class="page-nav">
    <#list _pages()! as page>
        <span><a href="${page.page_url}">${page.page_name}</a></span>
    </#list>
    </p>
    <hr/>
</header>
<main>
