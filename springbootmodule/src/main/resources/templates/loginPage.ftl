<#import "/spring.ftl" as spring />
<html>

<head>
    <title>login</title>
</head>

<body>
    <form action="login" method="post" />
    <@spring.bind path="user" /> login
    <@spring.formInput "user.login"/>
    <@spring.showErrors '<br>', 'fieldError' /> password
    <@spring.formInput "user.password"/>
    <@spring.showErrors '<br>', 'fieldError' />
    <input class="button" type="submit" value="submit" />
    </form>
    <a href="registration">Registration</a>
</body>

</html>
