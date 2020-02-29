function submitForm(){
    $("#comment_form").ajaxSubmit({
        success: function (data) {
            if (confirm("回复成功,是否刷新")) {
                location.reload();
            }
        },
        error: function (data) {
            console.log(data);
            location.reload();
            alert(data.responseJSON.message);
        }
    })
    return false;
}

$(function () {
    $(".comment_reply").click(replyEvent);

    function replyEvent() {
        var comment_id = $(this).attr('comment_id');
        var article_id = $(this).attr('article_id');
        var nickname = $(this).siblings('b').first().text();
        var reply_div = createCommentDiv(comment_id,article_id,nickname);
        $(this).parent().append(reply_div);
    }

    var onLoadchildren = false;
    
    $(".show_children_comments").click(function () {
        var info = $(this).parent().siblings('span')
        var root_id = info.attr('comment_id');
        var article_id = info.attr('article_id');
        loadChildComments(1,root_id,article_id,$(this).siblings('.children_comments_panel'));
    });

    function loadChildComments(page,root_id,article_id,parent_div) {
        if (onLoadchildren) return;
        onLoadchildren=true;
        $.ajax({
            type:'get',
            url:'/api/comment/comments',
            data:{
                page:page,
                root_id:root_id
            },
            success:function (data) {
                onLoadchildren = false;
                addCommentsComponent(parent_div,data);
            },
            error:function (data) {
                onLoadchildren = false;
                console.log(data);
            }

        })
    }


    /**
     * 创建子评论
     * @param parent_div
     * @param data
     */
    function addCommentsComponent(parent_div,data) {
        parent_div.empty();
        var divs = new Array();
        data.pageItems.forEach(function (value) {
            var parent_nickname = value.comment_parent?value.comment_parent.comment_user_nickname:null;
            var comment = createComment(
                value.comment_user_nickname,
                parent_nickname,
                value.comment_body,
                value.comment_create_time,
                value.comment_id,
                value.comment_article_id);
            divs.push(comment);
            });
        parent_div.append(divs);

        var paging = createPagingTag(data.pageIndexes,
            data.currentPageNum,
            data.totalPageNums,
            data.pageItems[0].comment_root_id,
            data.pageItems[0].comment_article_id,
            parent_div);
        parent_div.siblings('.children_paging_tag').empty();
        parent_div.siblings('.children_paging_tag').append(paging);
    }

    /**
     *创建分页
     */
    function createPagingTag(pageArray,nowPage,total_page,root_id,article_id,parent_div) {
        var p =createElement('p');
        var span =createElement('span');
        var a = new Array();
        pageArray.forEach(function (value) {
            var page_item = createElement('a');
            $(page_item).attr('href','#');
            $(page_item).addClass('paging_tag');
            $(page_item).attr('comment_id',root_id);
            $(page_item).attr('article_id',article_id);
            $(page_item).text(value);
            $(page_item).click(function () {
                var root_id = $(this).attr('comment_id');
                var article_id = $(this).attr('article_id');
                var page = $(this).text();
                var parent_div = $(this).parent().parent().siblings('.children_comments_panel');
                console.log(root_id,article_id,page,parent_div)
                loadChildComments(page,root_id,article_id,parent_div);
            });
            a.push($(page_item));
        })

        $(span).text('共'+total_page+'页 ');
        $(p).append(span);
        $(p).append(a);

        return p;
    }

    /**
     * 动态创建子评论
     * @param nick_name
     * @param body
     * @param time
     * @param comment_id
     * @param article_id
     * @returns {jQuery|HTMLElement}
     */
    function createComment(nick_name,parent_nickname,body,time,comment_id,article_id) {
        var div = createElement('div');
        var b = createElement('b');
        var p=createElement('p');
        var i = createElement('i');
        var span = createElement('span');

        $(div).addClass('comment');
        $(b).addClass('text-tip');
        $(p).addClass('comment-body');
        $(span).addClass('comment_reply');

        $(span).click(replyEvent);

        $(b).text(nick_name);
        $(p).text(body);

        var date = new Date(time);
        $(i).text(date.getFullYear()+'-'+date.getMonth()+'-'+date.getDay());

        $(span).text('回复');

        $(span).attr('comment_id',comment_id);
        $(span).attr('article_id',article_id);

        $(div).append($(b));
        if (parent_nickname){
            var i2 = createElement('i');
            $(i2).text(' 回复');
            var parent = createElement('b');
            $(parent).text(parent_nickname);
            $(parent).addClass('text-tip');
            $(div).append($(i2));
            $(div).append($(parent));
        }


        $(div).append($(p));
        $(div).append($(i));
        $(div).append($(span));
        return $(div);
    }

    function createElement(type) {
        return document.createElement(type);
    }
    
    
    function createCommentDiv(c_id,a_id,name) {
        var lowComment = document.getElementById('comment_reply_div');
        $(lowComment).remove();

        var div = document.createElement('div');
        var textarea = document.createElement("textarea");
        var button = document.createElement("button");
        $(div).attr("id",'comment_reply_div')

        $(textarea).attr('placeholder','回复给：'+name);
        $(textarea).attr('required','required');
        $(textarea).attr('id','comment_reply_text')

        $(button).attr('id','comment_reply_button')
        $(button).text("发表回复");
        $(button).attr('comment_id',c_id);
        $(button).attr('article_id',a_id);
        $(button).click(reply);
        $(div).append($(textarea));
        $(div).append($(button));
        return $(div);
    }


    /**
     * 回复
     */
    function reply() {
        var comment_id =  $(this).attr('comment_id');
        var article_id = $(this).attr('article_id');
        $.ajax({
            type:'post',
            url:'/api/comment/comment',
            data:{
                parent_id:comment_id,
                comment_body:$("#comment_reply_text").val()
            },
            success:function (data,status) {
                alert("回复成功");
                location.reload();
            },
            error:function (data) {
                location.reload();
                alert(data.responseJSON.message);
            }
        })
    }



})