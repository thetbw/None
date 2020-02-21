<hr/>
<div class="comments_panel">
<span class="text-tip">评论:</span>
    <div class="add-comment">
        <label class="add-comment-label" for="add-comment-switch">添加评论▼</label><br/>
        <input id="add-comment-switch" type="checkbox">
        <form action="${article.article_id}/comment" method="post" class="add-comment-form">
            <#if userInfo("login")??>
                <p><b>当前登陆：</b>${userInfo("login").user_nickname} <a href="/exit">退出登陆</a></p>
                内容：
                <textarea name="text" placeholder="发表回复" required></textarea>
                <button type="submit">回复</button>
            <#else >
                <a href="/login"><b>登陆</b></a>
                <#if blogProperty("commentMustLogin")>
                    <p><b>必须登陆才能评论</b></p>
                <#else >
                    <p>
                    <span>
                        <label for="email">邮箱</label>
                        <input type="email" name="email" id="email" required>
                    </span>
                    </p>
                    内容：<textarea name="text" required></textarea>
                    <button type="submit">发表评论</button>
                </#if>
            </#if>
        </form>
    </div>
<div>
<#list comment_list as comment>
    <div class="comment">
        <b class="text-tip">${comment.comment_user_nickname!} :</b>
        <p class="comment-body">${comment.comment_body}</p>
        <i >${toTime(comment.comment_create_time)}</i>
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
    <script src="/${themeInfo("themeFilePath")}/js/comment_script.js"></script>
</div>
