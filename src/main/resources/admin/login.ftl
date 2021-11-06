<#include "./header.ftl">
<main>
    <div id="content-pane">
        <div class="title"><h2>Login</h2></div>
        <div>
            <form action="/login" method="post" id="form" enctype="text/plain">
                <input type="text" class="form-control  login" placeholder="用户名" name="user_name" maxlength="12"
                       required>
                <input type="password" class="form-control  login" placeholder="密码" name="user_pass" maxlength="32"
                       required>
                <button type="button" class="btn btn-primary" id="login-button">登陆</button>
                <button type="button" class="btn btn-success" id="github-button">Github登录</button>
                <#if _property("isCanRegister")>
                    <button type="button" class="btn btn-info" onclick="register()">注册</button>
                </#if>
                <p><input class="radio" type="checkbox" name="auto_login" id="auto-login">下次自动登陆</p>

                <div>
                    <button type="button" class="btn btn-success" id="github-button">Github登录</button>
                </div>
            </form>
        </div>
        <a href="/">返回首页</a>
    </div>
</main>
<script>
    function GetQueryValue(queryName) {
        var reg = new RegExp("(^|&)" + queryName + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURI(r[2]);
        } else {
            return null;
        }
    }

    function register(){
        var referer = GetQueryValue("referer");
        if (referer){
            window.open('/register?referer='+GetQueryValue("referer"),'_self');
        }else {
            window.open('/register?referer=/','_self');
        }

    }

    $(function () {
        $("#login-button").click(function () {
            $("#form").ajaxSubmit({
                success: function (data) {
                    if(window.confirm("登陆成功，回到之前页面？")){
                        var referer = GetQueryValue("referer");
                        if (referer){
                            window.open(GetQueryValue("referer"),"_self")
                        }else {
                            window.open('/','_self');
                        }

                    }

                },
                error: function (data) {
                    console.log(data);
                    alert(data.responseJSON.message);
                    $("#form")[0].reset();
                }
            })
        });

        $("#github-button").click(function (){
            window.open('/githubLogin?referer='+GetQueryValue("referer"),'_self');
        })
    });

</script>
<#include "./footer.ftl">