<#include "header.ftl">

<#--<?php if ($this->options->postshowthumb == 'able'): if (array_key_exists('thumb',unserialize($this->___fields()))): ?>-->
<#--<div class="post-header-thumb <?php if ($this->options->colorBgPosts == 'defaultColor'): ?> bg-deepgrey<?php elseif ($this->options->colorBgPosts == 'customColor'): ?><?php if (array_key_exists('green',unserialize($this->___fields()))): ?> bg-green<?php elseif (array_key_exists('red',unserialize($this->___fields()))): ?> bg-red<?php elseif (array_key_exists('yellow',unserialize($this->___fields()))): ?> bg-yellow<?php elseif (array_key_exists('blue',unserialize($this->___fields()))): ?> bg-blue<?php elseif (array_key_exists('purple',unserialize($this->___fields()))): ?> bg-purple<?php else : ?> bg-<?php echo randBgColor(); ?><?php endif; ?><?php endif; ?>" style="background-image:url(<?php parseFieldsThumb($this);?>);">-->
	<#--<div class="post-header-thumb-op" style="background-image:url(<?php parseFieldsThumb($this);?>);"></div>-->
	<#--<div class="post-header-thumb-cover">-->
		<#--<div class="post-header-thumb-container">-->
			<#--<div class="post-header-thumb-title">-->
				<#--<?php $this->title() ?>-->
			<#--</div>-->
			<#--<div class="post-header-thumb-meta">-->
				<#--<time datetime="<?php $this->date('c'); ?>" itemprop="datePublished">Published on <?php $this->date('M j, Y'); ?></time> in <?php $this->category(''); ?> with <a href="#comments"><?php $this->commentsNum(_t(' 0 comment'), _t(' 1 comment'), _t(' %d comments')); ?></a>-->
			<#--</div>-->
			<#--<div class="post-tags">-->
				<#--<?php $this->tags(' ', true, ''); ?>-->
			<#--</div>-->
		<#--</div>-->
	<#--</div>-->
<#--</div>-->
<#--<?php else : ?>-->
<#--<?php $thumb = showThumb($this,null,true);?>-->
<#--<?php if(!empty($thumb)):?>-->
<#--<div class="post-header-thumb <?php if ($this->options->colorBgPosts == 'defaultColor'): ?> bg-deepgrey<?php elseif ($this->options->colorBgPosts == 'customColor'): ?><?php if (array_key_exists('green',unserialize($this->___fields()))): ?> bg-green<?php elseif (array_key_exists('red',unserialize($this->___fields()))): ?> bg-red<?php elseif (array_key_exists('yellow',unserialize($this->___fields()))): ?> bg-yellow<?php elseif (array_key_exists('blue',unserialize($this->___fields()))): ?> bg-blue<?php elseif (array_key_exists('purple',unserialize($this->___fields()))): ?> bg-purple<?php else : ?> bg-<?php echo randBgColor(); ?><?php endif; ?><?php endif; ?>">-->
	<#--<div class="post-header-thumb-op" style="background-image:url(<?php echo $thumb;?>);"></div>-->
	<#--<div class="post-header-thumb-cover">-->
		<#--<div class="post-header-thumb-container">-->
			<#--<div class="post-header-thumb-title">-->
				<#--<?php $this->title() ?>-->
			<#--</div>-->
			<#--<div class="post-header-thumb-meta">-->
				<#--<time datetime="<?php $this->date('c'); ?>" itemprop="datePublished">Published on <?php $this->date('M j, Y'); ?></time> in <?php $this->category(''); ?> with <a href="#comments"><?php $this->commentsNum(_t(' 0 comment'), _t(' 1 comment'), _t(' %d comments')); ?></a>-->
			<#--</div>-->
			<#--<div class="post-tags">-->
				<#--<?php $this->tags(' ', true, ''); ?>-->
			<#--</div>-->
		<#--</div>-->
	<#--</div>-->
<#--</div>-->
<#--<?php else : ?>-->
<#--<div class="post-header-thumb <?php if ($this->options->colorBgPosts == 'defaultColor'): ?> bg-deepgrey<?php elseif ($this->options->colorBgPosts == 'customColor'): ?><?php if (array_key_exists('green',unserialize($this->___fields()))): ?> bg-green<?php elseif (array_key_exists('red',unserialize($this->___fields()))): ?> bg-red<?php elseif (array_key_exists('yellow',unserialize($this->___fields()))): ?> bg-yellow<?php elseif (array_key_exists('blue',unserialize($this->___fields()))): ?> bg-blue<?php elseif (array_key_exists('purple',unserialize($this->___fields()))): ?> bg-purple<?php else : ?> bg-<?php echo randBgColor(); ?><?php endif; ?><?php endif; ?>">-->
	<#--<div class="post-header-thumb-op" style="background-image:url(<?php $this->options->themeUrl('images/thumbs/'.mt_rand(0,9).'.jpg'); ?>);"></div>-->
	<#--<div class="post-header-thumb-cover">-->
		<#--<div class="post-header-thumb-container">-->
			<#--<div class="post-header-thumb-title">-->
				<#--<?php $this->title() ?>-->
			<#--</div>-->
			<#--<div class="post-header-thumb-meta">-->
				<#--<time datetime="<?php $this->date('c'); ?>" itemprop="datePublished">Published on <?php $this->date('M j, Y'); ?></time> in <?php $this->category(''); ?> with <a href="#comments"><?php $this->commentsNum(_t(' 0 comment'), _t(' 1 comment'), _t(' %d comments')); ?></a>-->
			<#--</div>-->
			<#--<div class="post-tags">-->
				<#--<?php $this->tags(' ', true, ''); ?>-->
			<#--</div>-->
		<#--</div>-->
	<#--</div>-->
<#--</div>-->
<#--<?php endif;endif;endif; ?>-->

<article class="main-content page-page" itemscope itemtype="http://schema.org/Article">
	<div class="post-header">
		<h1 class="post-title" itemprop="name headline">
			${article.article_title}
		</h1>
		<div class="post-data">
			<time datetime="${toTime(article.article_create_time)}" itemprop="datePublished"/>Published on ${toTime(article.article_create_time)} with <a href="#comments">${article.article_comment_count}</a>
		</div>
	</div>
	<div id="post-content" class="post-content" itemprop="articleBody">
        <p class="post-tags">
            <#--<?php $this->tags(' ', true, ''); ?>-->
        </p>
		<#if required_pass>
			<p>需要密码</p>
			<form action="/api/article/${article.article_id}" method="post" autocomplete="off">
				<input type="password" name="access_pass" autofocus required>
				<input type="hidden" name="pass_only" value="true">
				<button type="submit">确认</button>
			</form>
		<#else >
		</#if>
		${article_content!}
		<p class="post-info">
			本文由 <a href="<?php $this->author->permalink(); ?>">${userInfo("admin").user_name}</a> 创作，采用 <a href="https://creativecommons.org/licenses/by/4.0/" target="_blank" rel="external nofollow">知识共享署名4.0</a> 国际许可协议进行许可<br>本站文章除注明转载/出处外，均为本站原创或翻译，转载前请务必署名<br>最后编辑时间为: ${article.article_update_time}
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
			<#--<span class="bottom-bar-item"><?php theNext($this); ?></span>-->
			<#--<span class="bottom-bar-item"><?php thePrev($this); ?></span>-->
			<span class="bottom-bar-item"><a href="#footer">↓</a></span>
			<span class="bottom-bar-item"><a href="#">↑</a></span>
		</div>
	</div>
</div>

<#--<?php if ($this->options->relatedPosts == 'able'): ?>-->
<#--<?php $this->related(6)->to($relatedPosts); ?>-->
<#--<?php if($relatedPosts->have()): ?>-->
<#--<div class="related-post-lists">-->
	<#--<div class="post-lists">-->
		<#--<div class="post-lists-body">-->
		<#--<?php while($relatedPosts->next()): ?>-->
			<#--<div class="post-list-item">-->
				<#--<div class="post-list-item-container">-->
					<#--<div class="item-label">-->
						<#--<div class="item-title"><a href="<?php $relatedPosts->permalink() ?>"><?php $relatedPosts->title() ?></a></div>-->
						<#--<div class="item-meta clearfix">-->
							<#--<?php if (array_key_exists('book',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-book" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('game',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-game" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('note',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-note" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('chat',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-chat" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('code',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-code" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('image',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-image" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('web',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-web" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('link',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-link" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('design',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-design" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php elseif (array_key_exists('lock',unserialize($relatedPosts->___fields()))): ?>-->
							<#--<div class="item-meta-ico bg-ico-lock" style="background: url(<?php $relatedPosts->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
							<#--<?php else : ?>-->
							<#--<div class="item-meta-ico bg-ico-<?php echo randBgIco(); ?>" style="background: url(<?php $this->options->themeUrl('images/bg-ico.png'); ?>) no-repeat;background-size: 40px auto;"></div>-->
	                        <#--<?php endif; ?>-->
							<#--<div class="item-meta-cat"><?php $relatedPosts->category(''); ?></div>-->
						<#--</div>-->
					<#--</div>-->
				<#--</div>-->
			<#--</div>-->
		<#--<?php endwhile; ?>-->
		<#--</div>-->
	<#--</div>-->
<#--</div>-->
<#--<?php endif; ?>-->
<#--<?php endif; ?>-->
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