<#include "header.ftl">
<style>
    @media (max-width: 580px){
        .categories{
            margin-top: 1rem;
        }
    }
</style>
<div class="main-content archive-page clearfix categories" >
    <div class="categorys-item">
        <#if category??>
            <div class="categorys-title">
                <a href="#">${category.category_name}</a><span> ：${category.category_article_num}</span>
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

        <#if categories??>
            <@treeList tree=categories></@treeList>
        </#if>
    </div>

    <#if articles??>
        <#list articles >
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
                                             style="background: url('/${_theme("themeFilePath")}/images/bg-ico.png');no-repeat;background-size: 40px auto;"></div>
                                        <div class="item-meta-date">${_u_time(article.article_create_time)}</div>
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
            <#list pagination! as page_num>
                <li class="<#if page_num.current>current</#if>"><a href="${page_num.pageUrl}">#{page_num.page}</a></li>
            </#list>
        </ol>
    </div>

</div>

<#include "footer.ftl">