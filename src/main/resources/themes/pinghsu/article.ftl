<#include "header.ftl">

<article class="main-content page-page" itemscope itemtype="http://schema.org/Article">
	<div class="post-header">
		<h1 class="post-title" itemprop="name headline">
			${article.article_title}
		</h1>
		<div class="post-data">
			<time datetime="${_u_time(article.article_create_time)}" itemprop="datePublished"></time><b>发布日期:</b>${_u_time(article.article_create_time)} <b> 评论:</b> ${article.article_comment_count}  <b> 浏览:</b>${article.article_browsed_count}
		</div>
	</div>
	<div id="post-content" class="post-content" itemprop="articleBody">
        <p class="post-tags">
			<#list article.article_tags as tag>
				<a href="/tag/${tag.tag_id}">${tag.tag_name}</a>
			</#list>
        </p>
		<#if need_pass!>
			<p>需要密码</p>
			<form action="/api/article/${article.article_id}" method="post" autocomplete="off">
				<input type="password" name="access_pass" autofocus required>
				<input type="hidden" name="pass_only" value="true">
				<button type="submit">确认</button>
			</form>
		<#else >
			${article.article_content.content_body!}
		</#if>
		<p class="post-info">
			最后编辑时间为: ${_u_time(article.article_update_time)}
		</p>
	</div>
</article>

<div id="post-bottom-bar" class="post-bottom-bar">
	<div class="bottom-bar-inner">
		<div class="bottom-bar-items social-share left">
			<span class="bottom-bar-item">Share : </span>
			<span class="bottom-bar-item bottom-bar-facebook"><a href="https://www.facebook.com/sharer/sharer.php?u=thetbw.xyz" target="_blank" title="${article.article_title}" rel="nofollow">facebook</a></span>
			<span class="bottom-bar-item bottom-bar-twitter"><a href="https://twitter.com/intent/tweet?url=thetbw.xyz&text=${article.article_title}" target="_blank" title="${article.article_title}" rel="nofollow">Twitter</a></span>
			<span class="bottom-bar-item bottom-bar-weibo"><a href="http://service.weibo.com/share/share.php?url=thetbw.xyz&amp;title=${article.article_title}" target="_blank" title="${article.article_title}" rel="nofollow">Weibo</a></span>
			<span class="bottom-bar-item bottom-bar-qrcode"><a href="//pan.baidu.com/share/qrcode?w=300&amp;h=300&amp;url=thetbw.xyz" target="_blank" rel="nofollow">QRcode</a></span>
		</div>
		<div class="bottom-bar-items right">
			<span class="bottom-bar-item"><a href="#footer">↓</a></span>
			<span class="bottom-bar-item"><a href="#">↑</a></span>
		</div>
	</div>
</div>

<div id="directory-content" class="directory-content">
	<div id="directory"></div>
</div>
<script>
	var postDirectoryBuild = function() {
		var postChildren = function children(childNodes, reg) {
					var result = [],
							isReg = typeof reg === 'object',
							isStr = typeof reg === 'string',
							node, i, len;
					for (i = 0, len = childNodes.length; i < len; i++) {
						node = childNodes[i];
						if ((node.nodeType === 1 || node.nodeType === 9) &&
								(!reg ||
										isReg && reg.test(node.tagName.toLowerCase()) ||
										isStr && node.tagName.toLowerCase() === reg)) {
							result.push(node);
						}
					}
					return result;
				},
				createPostDirectory = function(article, directory, isDirNum) {
					var contentArr = [],
							titleId = [],
							levelArr, root, level,
							currentList, list, li, link, i, len;
					levelArr = (function(article, contentArr, titleId) {
						var titleElem = postChildren(article.childNodes, /^h\d$/),
								levelArr = [],
								lastNum = 1,
								lastRevNum = 1,
								count = 0,
								guid = 1,
								id = 'directory' + (Math.random() + '').replace(/\D/, ''),
								lastRevNum, num, elem;
						while (titleElem.length) {
							elem = titleElem.shift();
							contentArr.push(elem.innerHTML);
							num = +elem.tagName.match(/\d/)[0];
							if (num > lastNum) {
								levelArr.push(1);
								lastRevNum += 1;
							} else if (num === lastRevNum ||
									num > lastRevNum && num <= lastNum) {
								levelArr.push(0);
								lastRevNum = lastRevNum;
							} else if (num < lastRevNum) {
								levelArr.push(num - lastRevNum);
								lastRevNum = num;
							}
							count += levelArr[levelArr.length - 1];
							lastNum = num;
							elem.id = elem.id || (id + guid++);
							titleId.push(elem.id);
						}
						if (count !== 0 && levelArr[0] === 1) levelArr[0] = 0;

						return levelArr;
					})(article, contentArr, titleId);
					currentList = root = document.createElement('ul');
					dirNum = [0];
					for (i = 0, len = levelArr.length; i < len; i++) {
						level = levelArr[i];
						if (level === 1) {
							list = document.createElement('ul');
							if (!currentList.lastElementChild) {
								currentList.appendChild(document.createElement('li'));
							}
							currentList.lastElementChild.appendChild(list);
							currentList = list;
							dirNum.push(0);
						} else if (level < 0) {
							level *= 2;
							while (level++) {
								if (level % 2) dirNum.pop();
								currentList = currentList.parentNode;
							}
						}
						dirNum[dirNum.length - 1]++;
						li = document.createElement('li');
						link = document.createElement('a');
						link.href = '#' + titleId[i];
						link.innerHTML = !isDirNum ? contentArr[i] :
								dirNum.join('.') + ' ' + contentArr[i] ;
						li.appendChild(link);
						currentList.appendChild(li);
					}
					directory.appendChild(root);
				};
		createPostDirectory(document.getElementById('post-content'),document.getElementById('directory'), true);
	};
	postDirectoryBuild();
</script>


<script src="//cdn.bootcss.com/headroom/0.9.1/headroom.min.js"></script>
<script src="//cdn.bootcss.com/highlight.js/9.10.0/highlight.min.js"></script>
<script>
	var postDirectory = new Headroom(document.getElementById("directory-content"), {
		tolerance: 0, offset : 90, classes: {
		initial: "initial",
				pinned: "pinned",
				unpinned: "unpinned"
	}
	});
	postDirectory.init();
	var postSharer = new Headroom(document.getElementById("post-bottom-bar"), {
		tolerance: 0,
		offset : 70,
		classes: {
			initial: "animated",
			pinned: "pinned",
			unpinned: "unpinned"
		}
	});
	postSharer.init();
	var header = new Headroom(document.getElementById("header"), {
		tolerance: 0,
		offset : 70,
		classes: {
			initial: "animated",
			pinned: "slideDown",
			unpinned: "slideUp"
		}
	});
	header.init();
	hljs.initHighlightingOnLoad();
	if ('addEventListener' in document) {
		document.addEventListener('DOMContentLoaded', function() {
			FastClick.attach(document.body);
		}, false);
	}
</script>
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
    showProcessingMessages: false,
    messageStyle: "none",
    extensions: ["tex2jax.js"],
    jax: ["input/TeX", "output/HTML-CSS"],
    tex2jax: {
        inlineMath: [ ['$','$'], ["\\(","\\)"] ],
        displayMath: [ ['$$','$$'], ["\\[","\\]"] ],
        skipTags: ['script', 'noscript', 'style', 'textarea', 'pre','code','a'],
        ignoreClass:"comment-content"
    },
    "HTML-CSS": {
        availableFonts: ["STIX","TeX"],
        showMathMenu: false
    }
});
MathJax.Hub.Queue(["Typeset",MathJax.Hub]);
</script>
<script src="//cdn.bootcss.com/mathjax/2.7.0/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>



<#include "comments.ftl">
<#include "footer.ftl">