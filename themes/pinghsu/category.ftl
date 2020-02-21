<#include "header.ftl">

<div class="main-content archive-page clearfix">
    <div class="categorys-item">
        <#if now_category??>
            <div class="categorys-title">
                <a href="#">${now_category.category_name}</a><span> ：${now_category.category_article_num}</span>
            </div>
        </#if>
        <#macro treeList tree>
            <#if tree??&&tree?size gt 0>
                <#list tree>
                    <div class="post-lists">
                        <#items as child>
                            <div class="post-lists-body">
                                <div class="post-list-item">
                                    <div class="post-list-item-container">
                                        <div class="item-label">
                                            <div class="item-title"><a
                                                        href="/category/${child.category_id}">${child.category_name}</a><br>

                                            </div>
                                            <div class="item-meta clearfix">
                                                <div class="item-meta-date"> ${child.category_article_num}篇文章</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#items>
                    </div>
                </#list>
            </#if>
        </#macro>

        <#if category_tree??>
            <@treeList tree=category_list></@treeList>
        </#if>
    </div>

    <#if category_articles??>
        <#list category_articles >
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
            <#list category_pagination! as page_num>
                <li class="<#if page_num.nowPage>current</#if>"><a href="${page_num.pageUrl}">#{page_num.page}</a></li>
            </#list>
        </ol>
    </div>

</div>

<#include "footer.ftl">