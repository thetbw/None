<#include "header.ftl">
    <#if tag_list??>
        <div class="tag_panel">
            <#list tag_list as tag>
                <a href="/tag/${tag.tag_id}">${tag.tag_name}</a>
            </#list>
        </div>
    <#else >
        <div class="tag_article item-list">
            <p><b>#${now_tag.tag_name}</b></p>
            <#if tag_articles?size==0>
                <b>此标签没有文章</b>
            <#else >
                <#list tag_articles>
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
            <#list tag_pagination! as page>
                <a href="/tag/<#if now_tag??>${now_tag.tag_id}</#if>?page=${page}">${page}</a>
            </#list>
            </span>
        </div>
    </#if>

<#include "footer.ftl">