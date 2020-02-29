<#include "header.ftl">
    <div>
        <p class="article-title">${article.article_title}</p>
        <p class="text-tip article-info ">
            发布日期：${_u_time(article.article_create_time)}
            &nbsp;&nbsp;浏览：${article.article_browsed_count}
            &nbsp;&nbsp;评论：${article.article_comment_count}</p>
    </div>
    <article class="article-content">
        <#if need_pass>
            <p>需要密码</p>
            <form action="/api/article/${article.article_id}" method="post" autocomplete="off">
                <input type="password" name="access_pass" autofocus required>
                <input type="hidden" name="pass_only" value="true">
                <button type="submit">确认</button>
            </form>
        <#else >
        </#if>
        ${article.article_content.content_body!}
    </article>
<#if _property("commentOpened")>
    <#include "comments.ftl">
</#if>
<#include "footer.ftl">