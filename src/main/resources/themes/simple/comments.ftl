<hr/>
<div class="comments_panel">
<span class="text-tip">评论:</span>
    <div class="add-comment">
        <label class="add-comment-label" for="add-comment-switch">添加评论▼</label><br/>
        <input id="add-comment-switch" type="checkbox">
        <form action="/api/comment/comment" method="post" class="add-comment-form" id="comment_form" onsubmit="return submitForm()">
            <#if _user("login")??>
                <p><b>当前登陆：</b>${_user("login").user_nickname} <a href="/exit">退出登陆</a></p>
                内容：
                <input type="hidden" name="article_id" value="${article.article_id}">
                <textarea name="comment_body" placeholder="发表回复" required></textarea>
                <button type="submit">回复</button>
            <#else >
                <a href="/login"><b>登陆</b></a>
                <#if _property("commentMustLogin")>
                    <p><b>必须登陆才能评论</b></p>
                <#else >
                    <p>
                    <span>
                        <label for="email">邮箱</label>
                        <input type="email" name="user_email" id="email" required>
                    </span>
                    </p>
                    <input type="hidden" name="article_id" value="${article.article_id}">
                    内容：<textarea name="comment_body" required></textarea>
                    <button type="submit">发表评论</button>
                </#if>
            </#if>
        </form>
    </div>
<div>
<#list comments as comment>
    <div class="comment">
        <b class="text-tip">${comment.comment_user_nickname!} :</b>
        <p class="comment-body">${comment.comment_body}</p>
        <i >${_u_time(comment.comment_create_time)}</i>
        <span class="comment_reply" comment_id="${comment.comment_id}" article_id="${article.article_id}">回复</span>
        <#if comment.comment_children_mun gt 0>
            <div class="children_comments">
                <a>共${comment.comment_children_mun}个回复 <a href="#" class="show_children_comments">点击查看</a></>
                <div class="children_comments_panel"></div>
                <div class="children_paging_tag"></div>
            </div>
        </#if>

        <#--<div>-->
            <#--<textarea placeholder="回复给:${comment.comment_user_nickname!}" required></textarea>-->
            <#--<button type="button">发表回复</button>-->
        <#--</div>-->
        <hr />
    </div>
<#else >
    <p >暂时没有评论</p>
</#list>
    <script src="/${_theme("themeFilePath")}/js/jquery.form.min.js"></script>
    <script src="/${_theme("themeFilePath")}/js/comment_script.js"></script>
</div>
