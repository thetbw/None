</main>
<hr/>
<footer>
    &copy;2020 ${userInfo("admin").user_nickname!"null"} <i>theme</i> <a href="/">${themeInfo("themeName")!"null"}</a><br/>
    <i>power by <a href="/">${blogInfo("BLOG_NAME")}</a></i>

</footer>
<a class="back-top" href="#header">↑</a>
<script>
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