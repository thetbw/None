<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <title>${_info("BLOG_NAME")}</title>
    <link rel="icon shortcut" type="image/x-icon" href="${_property("siteFavicon")}">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script src="./js/jquery-3.4.1.min.js"></script>
    <script src="./js/jquery.form.min.js"></script>
    <style>
        main{
            text-align: center;
            margin-top: 3rem;
        }
        form{
            max-width: 600px;
            padding: 50px;
            border-radius: 25px;
            text-align: left;
            margin: 2rem auto;
            background-color: rgba(184, 203, 214, 0.24);
        }

    </style>
</head>
<body>
    <main>
        <h4><b>创建管理员账户</b></h4>
        <form onsubmit="return f()" action="/install" method="post">
            <div class="form-group">
                <label for="userName">用户名</label>
                <input type="text" class="form-control" id="userName" name="user_name" required>
            </div>
            <div class="form-group">
                <label for="userPass">密码</label>
                <input type="password" class="form-control" id="userPass" name="user_pass" required>
            </div>
            <div class="form-group">
                <label for="userPass2">确认密码</label>
                <input type="password" class="form-control" id="userPass2" required>
            </div>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
    </main>
    <script>
        function f() {
            var pw1 = document.getElementById("userPass").value;
            var pw2 = document.getElementById("userPass2").value;
            if (pw1!=pw2){
                alert("两次密码不一致")
                return false;
            }
            return true;
        }
    </script>

</body>
</html>