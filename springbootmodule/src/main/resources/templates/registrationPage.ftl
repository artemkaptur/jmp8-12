<#import "/spring.ftl" as spring />
<html>

<head>
    <title>Registration</title>
</head>

<body>
    <form action="registration" method="post">
        <@spring.bind path="user" /> login
        <@spring.formInput "user.login"/>
        <br>
        <@spring.showErrors "<br>"/>
        <br> password
        <@spring.formPasswordInput "user.password"/>
        <br>
        <@spring.showErrors "<br>"/>
        <br> role
        <@spring.formCheckbox "user.isAdmin"/>
        <input class="button" type="submit" value="submit" />
        </form:form>
</body>

</html>
