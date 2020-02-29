<#include "header.ftl">
    <#if tags??>
        <div class="tag_panel">
            <#list tags as tag>
                <a href="/tag/${tag.tag_id}">${tag.tag_name}</a>
            </#list>
        </div>
    <#else >
        <div class="tag_article item-list">
            <p><b>#${tag.tag_name}</b></p>
            <#if articles?size==0>
                <b>此标签没有文章</b>
            <#else >
                <#list articles>
                    <ul>
                        <#items as article>
                            <li class="article_item">
                                <a href="/article/${article.article_id}">${article.article_title}</a>
                            </li>
                        </#items>
                    </ul>

                </#list>
            </#if>
            <span class="pagination">
            <#list pagination! as page>
                <a href="${page.pageUrl}" class="pagination-item <#if page.current>current_page</#if> ">#{page.page}</a>
            </#list>
            </span>
        </div>
    </#if>

<#include "footer.ftl">