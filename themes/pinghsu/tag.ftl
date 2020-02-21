<#include "header.ftl">

<div class="main-content archive-page clearfix">
    <div class="categorys-item">
        <#if now_tag??>
            <div class="categorys-title">
                <a href="#">${now_tag.tag_name}</a><span> ：${now_tag.tag_article_num}</span>
            </div>
        </#if>
        <#macro treeList tree>
            <#if tree??&&tree?size gt 0>
                <#list tree>
                    <div class="post-lists">
                        <div class="post-lists-body">
                        <#items as child>
                            <a  href="/tag/${child.tag_id}" style="display: inline-block;
                                padding: 5px 15px;background-color: #6fa3ef;margin: 0.8rem 1rem ;border-radius: 15px;cursor: pointer;font-weight: 600;color: white;">#${child.tag_name}</a>
                        </#items>
                        </div>
                    </div>
                </#list>
            </#if>
        </#macro>

        <#if tag_list??>
            <@treeList tree=tag_list></@treeList>
        </#if>
    </div>

    <#if tag_articles??>
        <#list tag_articles >
            <div class="post-lists">
                <div class="post-lists-body">
                    <#items  as article>
                        <#assign color=randomColor(true)!>
                        <div class="post-list-item">
                            <div class="post-list-item-container ${color}">
                                <div class="item-label ${color}">
                                    <div class="item-title"><a
                                                href="/article/${article.article_id}">${article.article_title}</a></div>
                                    <div class="item-meta clearfix">
                                        <div class="item-meta-ico bg-ico-book"
                                             style="background: url('/${themeInfo("themeFilePath")}/images/bg-ico.png');no-repeat;background-size: 40px auto;"></div>
                                        <div class="item-meta-date">${toTime(article.article_create_time)}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#items>
                </div>
            </div>
        </#list>


    </#if>


    <div class="lists-navigator clearfix">
        <ol class="lists-navigator">
            <#list pagination! as page>
                <li class="<#if page.nowPage>current</#if>"><a href="${page.pageUrl}">#{page.page}</a></li>
            </#list>
        </ol>
    </div>

</div>

<#include "footer.ftl">