
<footer id="footer" class="footer ">
	<div class="footer-social">
		<div class="footer-container clearfix">
			<div class="social-list">
                <a class="social rss" target="blank" href="/rss">RSS</a>
			</div>
		</div>
	</div>
	<div class="footer-meta">
		<div class="footer-container">
			<div class="meta-item meta-copyright">
				<div class="meta-copyright-info">
                    <a href="/" class="info-logo">
						${_property("siteTitle")}
                    </a>
					<div class="info-text">
                    	<p>Theme is <a href="https://github.com/chakhsu/pinghsu" target="_blank">Pinghsu</a> by <a href="https://www.linpx.com/" target="_blank">Chakhsu</a></p>
						<p>Powered by <a href="${_info("BLOG_URL")}" target="_blank" rel="nofollow">None</a></p>
						<p>&copy; 2020 <a href="/">${_property("siteTitle")}</a></p>
					</div>
				</div>
			</div>
			<div class="meta-item meta-posts">
				<h3 class="meta-title">公告</h3>
                ${_property("siteNotice")}
			</div>
		</div>
	</div>
</footer>

<script src="/${_theme("themeFilePath")}/js/instantclick.min.js"></script>
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
${_property("siteFooter")!}
</body>
</html>
