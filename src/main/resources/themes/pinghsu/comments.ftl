<div class="comment-container" style="margin-bottom: 1rem">
    <div id="comments" class="clearfix">

        <#macro comment>
            <div style="padding: 10px 0px 10px 20px;display: none" id="replay_info">
                正在回复 <b ></b>  <a style="cursor: pointer;color: blue" onclick="cancelReplay(this)">取消</a>
            </div>
            <form method="post" action="/api/comment/comment" id="comment_form" class="comment-form" role="form" onsubmit ="submitForm();getElementById('misubmit').disabled=true;return false;">
                <#if !_user("login")??>
                <input type="email" name="user_email"  class="form-control input-control clearfix" placeholder="Email (*)" value="" required>
                </#if>
                <input type="hidden" name="parent_id" value="">
                <input type="hidden" name="article_id" value="${article.article_id}">
                <textarea name="comment_body" id="textarea" class="form-control  " placeholder="Your comment here. Be cool. "  required ></textarea>
                <button type="submit" class="submit" id="misubmit">发表评论</button>

            </form>
        </#macro>
        <#if _property("commentOpened")>
            <#if _user("login")??>
                <span class="response" id="replay">当前登陆：<a href="#">${_user("login").user_nickname}</a> <a href="/exit" data-no-instant>退出登陆</a></span>
                <@comment></@comment>
            <#else >
                <span class="response"> <a href="/login" data-no-instant>登陆</a></span>
                <@comment></@comment>
            </#if>
        </#if>
        <#list comments>
            <script>
                var article_id=${article.article_id};
            </script>
            <ol class="comment-list">
                <#items as comment>
                    <li class="comment-body comment-parent comment-even">
                        <div class="comment-view">
                            <div class="comment-header">
                                <img class="avatar" src="${comment.comment_user.user_avatar_url!}" width="80" height="80">
                                <span class="comment-author">
                                    <a href="#" target="_blank" rel="external nofollow">${comment.comment_user_nickname!}</a>
                                </span>
                            </div>
                            <div class="comment-content">
                                <span class="comment-author-at"></span> <p>${comment.comment_body}</p><p></p>
                            </div>
                            <div class="comment-meta">
                                <time class="comment-time">${_u_time(comment.comment_create_time)}</time>
                                <span class="comment-reply"><a href="#replay"  comment_nickname="${comment.comment_user_nickname!}"  comment_id="${comment.comment_id}" onclick="jumpToReplay(this)">回复</a></span>
                            </div>
                        </div>
                        <#if comment.comment_children_mun gt 0>
                            <div comment_id=${comment.comment_id}>
                                <span style="margin-left: 1rem">共${comment.comment_children_mun}个回复
                                    <a style="cursor: pointer" class="show_children_comments">点击查看</a></span>
                                <div class="comment-list" style="margin-left: 1rem"></div>
                                <div class="children_paging_tag" style="text-align: right;margin-right: 1rem"></div>
                            </div>
                        </#if>

                    </li>
                </#items>
            </ol>
        <#else >
            <p >暂时没有评论</p>
        </#list>
        <#if pagination?size gt 1>
            <div class="lists-navigator clearfix">
                <ol class="lists-navigator">
                    <#list pagination! as page_num>
                        <li class="<#if page_num.current>current</#if>"><a href="${page_num.pageUrl}">#{page_num.page}</a></li>
                    </#list>
                </ol>
            </div>
        </#if>

        <script src="/${_theme("themeFilePath")}/js/jquery-3.4.1.min.js"></script>
        <script src="/${_theme("themeFilePath")}/js/jquery.form.min.js"></script>
        <script src="/${_theme("themeFilePath")}/js/comment_script.js" data-no-instant></script>
    </div>
</div>
