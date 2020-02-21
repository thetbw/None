<#include "./header.ftl">
    <div class="item-list">
    <ul class="article-list">
        <#list article_list! as article>
            <li class="article_item">
                <a href="/article/${article.article_id}">${article.article_title}</a>
                <#if article.article_is_top=1><i class="article-top">[置顶]</i></#if>
            </li>
        </#list>
    </ul>
    <span class="pagination">
        <#list article_pagination! as page_num>
            <a href="/index?page=${page_num}" class="pagination-item <#if page_num=now_page>current_page</#if> ">#{page_num}</a>
        </#list>
    </span>
    </div>

<#include "./footer.ftl">