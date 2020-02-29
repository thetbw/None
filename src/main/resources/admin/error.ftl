<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <title>${_property("siteTitle")} -error</title>
    <link rel="icon shortcut" type="image/x-icon" href="/favicon.ico">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="/css/error.css">
</head>
<body>
    <main>
        <h1>Error: ${error!}</h1>
        <div class="error-info">
            <b>错误路径：</b> ${path!}<br/>
            <b>错误状态：</b> ${status!}<br/>
            <b>错误信息：</b> ${message!}<br/>

        </div>
        <br/>
        <a href="/">返回主页</a>
    </main>

</body>
</html>