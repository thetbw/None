<#include "header.ftl">

<div class="main-content archive-page clearfix">
    <div class="categorys-item">
        <#if tag??>
            <div class="categorys-title">
                <a href="#">${tag.tag_name}</a><span> ：${tag.tag_article_num}</span>
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

        <#if tags??>
            <#if tags?size==0>
                <div style="height: 200;text-align: center">
                    <p>没有标签</p>
                </div>
            </#if>
            <@treeList tree=tags></@treeList>
        </#if>
    </div>

    <#if articles??>
        <#if articles?size==0>
            <div style="height: 200;text-align: center">
                <p>此标签下没有文章</p>
            </div>
        </#if>
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
            <#list pagination! as page>
                <li class="<#if page.current>current</#if>"><a href="${page.pageUrl}">#{page.page}</a></li>
            </#list>
        </ol>
    </div>

</div>

<#include "footer.ftl">