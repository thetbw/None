<#--<?php if (!defined('__TYPECHO_ROOT_DIR__')) exit; ?>-->
<#--<?php if (array_key_exists('archive',unserialize($this->___fields()))): ?>bg-white<?php elseif($this->is('archive')&&($this->options->colorBgPosts == 'defaultColor')): ?>bg-white<?php elseif($this->is('archive')&&($this->options->colorBgPosts == 'customColor')): ?>bg-grey<?php elseif($this->is('single')): ?>bg-white<?php endif; ?>-->
<footer id="footer" class="footer bg-grey">
	<#--<div class="footer-social">-->
		<#--<div class="footer-container clearfix">-->
			<#--<div class="social-list">-->
			<#--<?php if ($this->options->socialweibo): ?>-->
				<#--<a class="social weibo" target="blank" href="<?php $this->options->socialweibo(); ?>">WEIBO</a>-->
			<#--<?php endif; ?>-->
            <#--<?php if ($this->options->socialzhihu): ?>-->
                <#--<a class="social zhihu" target="blank" href="<?php $this->options->socialzhihu(); ?>">ZHIHU</a>-->
            <#--<?php endif; ?>-->
                <#--<a class="social rss" target="blank" href="<?php $this->options->siteUrl(); ?>feed/">RSS</a>-->
			<#--<?php if ($this->options->socialgithub): ?>-->
				<#--<a class="social github" target="blank" href="<?php $this->options->socialgithub(); ?>">GITHUB</a>-->
			<#--<?php endif; ?>-->
			<#--<?php if ($this->options->socialtwitter): ?>-->
				<#--<a class="social twitter" target="blank" href="<?php $this->options->socialtwitter(); ?>">TWITTER</a>-->
			<#--<?php endif; ?>-->
			<#--</div>-->
		<#--</div>-->
	<#--</div>-->
	<div class="footer-meta">
		<div class="footer-container">
			<div class="meta-item meta-copyright">
				<div class="meta-copyright-info">
                    <a href="/" class="info-logo">
                        <#--<?php if($this->options->footerLogoUrl): ?>-->
                        <#--<img src="<?php $this->options->footerLogoUrl();?>" alt="<?php $this->options->title() ?>" />-->
                        <#--<?php else : ?>-->
						${blogProperty("siteTitle")}
                        <#--<?php $this->options->title() ?>-->
                        <#--<?php endif; ?>-->
                    </a>
					<div class="info-text">
                    	<p>Theme is <a href="https://github.com/chakhsu/pinghsu" target="_blank">Pinghsu</a> by <a href="https://www.linpx.com/" target="_blank">Chakhsu</a></p>
						<p>Powered by <a href="/" target="_blank" rel="nofollow">None</a></p>
						<#--<p>&copy; <?php echo date('Y'); ?> <a href="<?php $this->options->siteUrl(); ?>"><?php $this->options->title(); ?></a></p>-->
						<p>&copy; 2020 <a href="/">${blogProperty("siteTitle")}</a></p>
					</div>
				</div>
			</div>
			<div class="meta-item meta-posts">
				<h3 class="meta-title">RECENT POSTS</h3>
                <#--<?php getRecentPosts($this,8); ?>-->
			</div>
            <div class="meta-item meta-comments">
                <h3 class="meta-title">RECENT COMMENTS</h3>
                <#--<?php $this->widget('Widget_Comments_Recent','pageSize=8')->to($comments); ?>-->
                <#--<?php while($comments->next()): ?>-->
                <#--<li><a href="<?php $comments->permalink(); ?>"><?php $comments->author(false); ?> : <?php $comments->excerpt(25, '...'); ?></a></li>-->
                <#--<?php endwhile; ?>-->
            </div>
		</div>
	</div>
</footer>

<script src="/${themeInfo("themeFilePath")}/js/instantclick.min.js"></script>
<script src="//cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
<script data-no-instant>
	InstantClick.on('change', function(isInitialLoad){

		var blocks = document.querySelectorAll('pre code');
		for (var i = 0; i < blocks.length; i++) {
			hljs.highlightBlock(blocks[i]);
		}
		if (isInitialLoad === false) {
			if (typeof ga !== 'undefined') ga('send', 'pageview', location.pathname + location.search);
			if (typeof MathJax !== 'undefined'){MathJax.Hub.Queue(["Typeset",MathJax.Hub]);}

		}
	});
	InstantClick.init('mousedown');
</script>
</body>
</html>
<#--<?php if (($this->options->tableOfContents == 'able') && ($this->is('post'))): ?>-->
