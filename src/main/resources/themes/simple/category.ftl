<#include "header.ftl">
    <#macro treeList tree>
       <#if tree??&&tree?size gt 0>
           <#list tree>
               <ul>
               <#items as child>
                   <li><a href="/category/${child.category_id}">${child.category_name}</a>
                       &nbsp;&nbsp;<i style="font-size: 0.7rem"><b>${child.category_article_num}</b>篇文章</i>
                   </li>
                   <#if child.category_children??&&child.category_children?size gt 0>
                       <@treeList tree=child.category_children></@treeList>
                   </#if>
               </#items>
               </ul>
           </#list>
       </#if>
    </#macro>
    <#if category??>
        <b>#${category.category_name}</b>
    </#if>
    <#if articles??>
        <div class="category_article item-list">
            <#if articles?size==0>
                <b>此分类没有文章</b>
            <#else >
                <ul>
                    <#list articles as article>
                        <li class="article_item">
                            <a href="/article/${article.article_id}">${article.article_title}</a>
                        </li>
                    </#list>
                </ul>
            </#if>
        </div>


    </#if>
    <#if categories??>
        <div class="categories item-list">
            <@treeList tree=categories></@treeList>
        </div>
    </#if>

    <#--<#list category_tree>-->
        <#--<ul>-->
            <#--<#items as child>-->
                <#--<li>${child.category_name}</li>-->
                <#--<ul>-->
                <#--<#list child.category_children! as c>-->
                    <#--<li>${c.category_name}</li>-->
                <#--</#list>-->
                <#--</ul>-->
            <#--</#items>-->
        <#--</ul>-->
    <#--</#list>-->
    <span class="pagination">
        <#list pagination! as page>
            <a href="${page.pageUrl}" class="pagination-item <#if page.current>current_page</#if> ">#{page.page}</a>
        </#list>
    </span>


<#include "footer.ftl">