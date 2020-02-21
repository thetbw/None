<#include "./header.ftl">
<main>
    <div id="content-pane">
        <div class="title"><h2>用户注册</h2></div>
        <div>
            <form action="/register" method="post" id="form">
                <input type="text" class="form-control" placeholder="用户名" name="user_name" maxlength="12" required>
                <input type="text" class="form-control" placeholder="昵称" name="user_nickname" maxlength="32" required>
                <input type="password" class="form-control" placeholder="密码" name="user_pass" id="pass1" maxlength="32" required>
                <input type="password" class="form-control " placeholder="确认密码" name="user_pass2"  id="pass2" maxlength="32" required>
                <input type="email" class="form-control" placeholder="邮箱" name="user_email" maxlength="32" required>

                <button type="button" class="btn btn-info" id="register_button">注册</button>
            </form>
        </div>
        <a href="/">返回首页</a>
    </div>
</main>
<script>
    function f() {
        var pw1 = document.getElementById("pass1").value;
        var pw2 = document.getElementById("pass2").value;
        if (pw1!=pw2){
            alert("两次密码不一致")
            return false;
        }
        return true;
    }
    $(function () {
        $("#register_button").click(function () {
            if (!f()) return;
            $("#form").ajaxSubmit({
                success:function (data) {
                    alert("登陆成功,即将前往登陆页面");
                    window.open("/login","_self");
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