<#import "/spring.ftl" as spring/>
<html>

<head>
    <title>Order</title>
</head>

<body>
    <#include "header.ftl">
        <br/>
        <br/> goods
        <table>
            <tr>
                <th>name</th>
                <th>image</th>
            </tr>
            <#list goods as good>
                <tr>
                    <td>${good.name}</td>
                    <td><img src="data:image/jpeg;charset=utf-8;base64,${good.base64}" width="150" height="150" /></td>
                </tr>
            </#list>
        </table>
        <form action="addOrder" method="post">
            <input class="button" type="submit" value="add order" />
        </form>
</body>

</html>
