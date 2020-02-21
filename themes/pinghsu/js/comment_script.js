function submitForm() {
    $("#comment_form").ajaxSubmit({
        success: function (data) {
            if (confirm("回复成功,是否刷新")) {
                location.reload();
            }
        },
        error: function (data) {
            console.log(data);
            alert(data.responseJSON.message);
        }
    })

};

function cancelReplay(t) {
    $('#comment_form').find("input[name='parent_id']").attr('value', "");
    $(t).find('b').text("");
    $(t).parent().hide();
};

function jumpToReplay(t) {
    var comment_id = $(t).attr('comment_id');
    var name = $(t).attr('comment_nickname');
    console.log(comment_id);
    $('#comment_form').find("input[name='parent_id']").attr('value', comment_id);
    $('#replay_info').show();
    $('#replay_info').find('b').text(name);
    return true;
};



var onLoadchildren = false;

$(".show_children_comments").click(function () {
    var info = $(this).parent().parent();
    var root_id = info.attr('comment_id');
    console.log(root_id);
    loadChildComments(1, root_id, info);
});

function loadChildComments(page, root_id, parent_div) {
    if (onLoadchildren) return;
    onLoadchildren = true;
    $.ajax({
        type: 'get',
        url: article_id + '/comment',
        data: {
            page: page,
            root_comment: root_id
        },
        success: function (data) {
            onLoadchildren = false;
            parent_div.find("span").remove();
            addCommentsComponent(parent_div.find(".comment-list"), data);
        },
        error: function (data) {
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
function addCommentsComponent(parent_div, data) {
    parent_div.empty();
    var divs = new Array();
    data.pageItems.forEach(function (value) {
        var parent_nickname = value.comment_parent ? value.comment_parent.comment_user_nickname : null;
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

    if (data.totalPageNums > 1) {
        var paging = createPagingTag(data.pageIndexes,
            data.currentPageNum,
            data.totalPageNums,
            data.pageItems[0].comment_root_id,
            data.pageItems[0].comment_article_id,
            parent_div);
        parent_div.siblings('.children_paging_tag').empty();
        parent_div.siblings('.children_paging_tag').append(paging);
    }
}

/**
 *创建分页
 */
function createPagingTag(pageArray, nowPage, total_page, root_id, article_id) {
    var p = createElement('p');
    var span = createElement('span');
    var a = new Array();
    pageArray.forEach(function (value) {
        var page_item = createElement('a');
        page_item.attr('href', '#');
        page_item.addClass('paging_tag');
        page_item.attr('comment_id', root_id);
        page_item.attr('article_id', article_id);
        page_item.text(value);
        page_item.click(function () {
            var root_id = $(this).attr('comment_id');
            var article_id = $(this).attr('article_id');
            var page = $(this).text();
            var parent_div = $(this).parent().parent().siblings('.children_comments_panel');
            console.log(root_id, article_id, page, parent_div)
            loadChildComments(page, root_id, article_id, parent_div);
        });
        a.push(page_item);
    })

    span.text('共' + total_page + '页 ');
    p.append(span);
    p.append(a);

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
function createComment(nick_name, parent_nickname, body, time, comment_id, article_id) {
    var div = createElement('div');
    var header = createElement('div');
    var content = createElement('div');
    var meta = createElement('div')

    div.addClass('comment-view');
    header.addClass('comment-header');
    content.addClass('comment-content');
    meta.addClass('comment-meta');

    var img = createElement('img');
    img.attr('src', 'https://secure.gravatar.com/avatar/d41d8cd98f00b204e9800998ecf8427e?s=50&r=G&d=mm');
    img.addClass('avatar');
    img.attr('width', '50');
    img.attr('height', '50');

    var u_span = createElement('span');
    u_span.addClass('comment-author');
    u_span.text(nick_name);

    header.append(img);
    header.append(u_span);

    var c_span = createElement('span');
    c_span.addClass('comment-author-at');
    if (parent_nickname)
        c_span.text('@' + parent_nickname);

    var c_p = createElement('p');
    c_p.text(body);

    content.append(c_span);
    content.append(c_p);

    var time = createElement('time');
    time.addClass('comment-time');
    var date = new Date(time);
    time.text(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDay())

    var m_span = createElement('span');
    m_span.addClass('comment-reply');
    var a = createElement('a');
    a.attr('rel', 'nofollow');
    a.text('回复');
    a.attr('comment_id', comment_id);
    a.attr('comment_nickname', nick_name);
    a.click(function () {
        jumpToReplay(this);
    });
    m_span.append(a)

    meta.append(time);
    meta.append(m_span);

    div.append(header);
    div.append(content);
    div.append(meta);

    return div;
}

function createElement(type) {
    return $(document.createElement(type));
}


function createCommentDiv(c_id, a_id, name) {
    var lowComment = document.getElementById('comment_reply_div');
    $(lowComment).remove();

    var div = document.createElement('div');
    var textarea = document.createElement("textarea");
    var button = document.createElement("button");
    $(div).attr("id", 'comment_reply_div')

    $(textarea).attr('placeholder', '回复给：' + name);
    $(textarea).attr('required', 'required');
    $(textarea).attr('id', 'comment_reply_text')

    $(button).attr('id', 'comment_reply_button')
    $(button).text("发表回复");
    $(button).attr('comment_id', c_id);
    $(button).attr('article_id', a_id);
    $(button).click(reply);
    $(div).append($(textarea));
    $(div).append($(button));
    return $(div);
}


/**
 * 回复
 */
function reply() {
    var comment_id = $(this).attr('comment_id');
    var article_id = $(this).attr('article_id');
    $.ajax({
        type: 'post',
        url: article_id + '/comment',
        data: {
            parent_id: comment_id,
            text: $("#comment_reply_text").val(),
        },
        success: function (data, status) {
            alert("回复成功");
            location.reload();
        },
        error: function (data) {
            alert(data.responseJSON.message);
        }
    })
}

