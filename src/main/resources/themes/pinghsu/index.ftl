
<#include "header.ftl">


<div class="main-content index-page clearfix">
	<div class="post-lists">
		<div class="post-lists-body">
			<#if articles?size==0>
				<div style="height: 200px;text-align: center;">
					<p>暂时没有文章</p>
				</div>
			</#if>
			<#list articles! as article>
			<div class="post-list-item">
				<div class="post-list-item-container">
					<div class="item-thumb bg-yellow" style="
							background-image:url('<#if article.article_cover??&&article.article_cover?length gt 1>${article.article_cover}<#else >/${_theme("themeFilePath")}/images/thumbs/${_u_random(0,0,9)}.jpg</#if>'););">
					</div>

					<a href="/article/${article.article_id}">
	                	<div class="item-desc">
							<p>${article.article_preview_text}</p>
						</div>
					</a>
					<div class="item-slant reverse-slant bg-purple"></div>
					<div class="item-slant"></div>
					<div class="item-label">
						<div class="item-title"><a href="/article/${article.article_id}">${article.article_title}</a></div>
						<div class="item-meta clearfix">

							<div class="item-meta-ico bg-ico-book" style="background: url('/${_theme("themeFilePath")}/images/bg-ico.png');no-repeat;background-size: 40px auto;"></div>

							<div class="item-meta-cat">
								<#if article.article_category??>
									<a href="/category/${article.article_category.category_id}">${article.article_category.category_name}</a>
								<#else >
									<a href="/category/0">默认分类</a>
								</#if>

							</div>
						</div>
					</div>
				</div>
			</div>
			</#list>
		</div>
	</div>
	<div class="lists-navigator clearfix">
		<ol class="lists-navigator">
			<#list pagination! as page_num>
				<li class="<#if page_num.current>current</#if>"><a href="${page_num.pageUrl}">#{page_num.page}</a></li>
			</#list>
		</ol>
  	</div>
</div>

<#include "footer.ftl">