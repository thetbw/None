<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no">
    <title>${blogProperty("siteTitle")}</title>
    <link rel="icon shortcut" type="image/x-icon" href="${blogProperty("siteFavicon")}">
    <meta name="description" content="${blogProperty("siteDescription")}">
    <meta name="keywords" content="${blogProperty("siteDescription")}">
    <meta name="theme" content="${themeInfo("themeName")}">
    <link type="text/css" rel="stylesheet" href="/${themeInfo("themeFilePath")}/css/style.css"/>
    <script src="/${themeInfo("themeFilePath")}/js/jquery-3.4.1.min.js"></script>
</head>
<body>
<header id="header">
    <div class="page-title">
        <h1><a href="/" style="text-decoration: none;color: black">${blogProperty("siteTitle")}</a></h1>
        <p class="description"><i >${blogProperty("siteDescription")}</i></p>
    </div>
    <p class="page-nav">
    <#list getPages()! as page>
        <span><a href="${page.page_url}">${page.page_name}</a></span>
    </#list>
    </p>
    <hr/>
</header>
<main>
