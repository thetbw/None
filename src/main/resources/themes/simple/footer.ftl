</main>
<hr/>
<footer>
    &copy;2020 ${_user("admin").user_nickname!"null"} <i>theme</i> <a href="/">${_theme("themeName")!"null"}</a><br/>
    <i>power by <a href="${_info("BLOG_URL")}" target="_blank">${_info("BLOG_NAME")}</a></i>

</footer>
<a class="back-top" onclick="backTop()">↑</a>
<script>
    function backTop(){
        $("body,html").animate({
            scrollTop:0
        })
    }

    $(document).scroll(function () {
        var h = $(document).scrollTop();
        if (h >500) {
            $('.back-top').css('display','block');
        }else {
            $('.back-top').css('display','none');
        }
    })

</script>
</body>
</html>