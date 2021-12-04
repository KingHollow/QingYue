# Interface 

### /Login

params (username, password)


return success: 成功；
       failed: 失败

### /Register

params (username, password, repassword)

return notequal: 密码不一致；
       tooshort: 密码不足六位；
       usernamerepeat: 用户名被注册；
       success: 成功