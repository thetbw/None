<#include "header.ftl">
    <div class="item-list">
    <ul class="article-list">
        <#list articles! as article>
            <li class="article_item">
                <a href="/article/${article.article_id}">${article.article_title}</a>
                <#if article.article_is_top=1><i class="article-top">[置顶]</i></#if>
            </li>
        </#list>
    </ul>
    <span class="pagination">
        <#list pagination! as page>
            <a href="${page.pageUrl}" class="pagination-item <#if page.current>current_page</#if> ">#{page.page}</a>
        </#list>
    </span>
    </div>

<#include "footer.ftl">