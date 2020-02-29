/**
 * 评论表单提交事件
 */
function submitForm() {
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

};

/**
 * 取消回复
 * @param t
 */
function cancelReplay(t) {
    $('#comment_form').find("input[name='parent_id']").attr('value', "");
    $(t).find('b').text("");
    $(t).parent().hide();
};

/**
 * 回复
 * @param t
 * @returns {boolean}
 */
function jumpToReplay(t) {
    var comment_id = $(t).attr('comment_id');
    var name = $(t).attr('comment_nickname');
    console.log(comment_id);
    $('#comment_form').find("input[name='parent_id']").attr('value', comment_id);
    $('#replay_info').show();
    $('#replay_info').find('b').text(name);
    scroll2dom($('#replay_info'));
    return true;
};


/**
 * 滚动到指定元素处
 * @param dom
 */
function scroll2dom(dom) {
    var scroll_offset = dom.offset(); //得到box这个div层的offset，包含两个值，top和left
    $("body,html").animate({
        scrollTop:scroll_offset.top-150 //让body的scrollTop等于pos的top，就实现了滚动
    })

}



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
        url: '/api/comment/comments',
        data: {
            page: page,
            root_id: root_id
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
            value.comment_article_id,
            value.comment_user);
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
function createComment(nick_name, parent_nickname, body, time, comment_id, article_id,user) {
    var div = createElement('div');
    var header = createElement('div');
    var content = createElement('div');
    var meta = createElement('div')

    div.addClass('comment-view');
    header.addClass('comment-header');
    content.addClass('comment-content');
    meta.addClass('comment-meta');

    var img = createElement('img');
    img.attr('src', user.user_avatar_url);
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

    var timeI = createElement('time');
    timeI.addClass('comment-time');
    var date = new Date(time);
    timeI.text(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate())

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

    meta.append(timeI);
    meta.append(m_span);

    div.append(header);
    div.append(content);
    div.append(meta);

    return div;
}

function createElement(type) {
    return $(document.createElement(type));
}




