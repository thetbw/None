<#include "./header.ftl">
<main>
    <div id="content-pane">
           <div class="title"><h2>Login</h2></div>
            <div>
                <form action="/login" method="post" id="form" enctype="text/plain">
                    <input type="text" class="form-control  login" placeholder="用户名" name="user_name" maxlength="12" required>
                    <input type="password" class="form-control  login" placeholder="密码" name="user_pass" maxlength="32" required>
                    <button type="button" class="btn btn-primary" id="login-button">登陆</button>
                    <#if blogProperty("isCanRegister")>
                        <button type="button" class="btn btn-info" onclick="window.open('/register','_self')">注册</button>
                    </#if>
                    <p><input class="radio" type="checkbox" name="auto_login" id="auto-login">下次自动登陆</p>
                </form>
            </div>
            <a href="/">返回首页</a>
    </div>
</main>
    <script>
        $(function () {
            $("#login-button").click(function () {
                $("#form").ajaxSubmit({
                    success:function (data) {
                        alert("登陆成功,即将跳转到首页");
                        window.open("/","_self");
                    },
                    error:function (data) {
                        console.log(data);
                        alert(data.responseJSON.message);
                        $("#form")[0].reset();
                    }
                })
            });
        });

    </script>
<#include "./footer.ftl">