<#import "/spring.ftl" as spring/>
<html>

<head>
    <title>Delivery</title>
</head>

<body>
    <#include "header.ftl">
        <br/>
        <br/> goods
        <table id="table">
            <tr>
                <th>name</th>
                <th>image</th>
                <th>deliveryStatus</th>
                <th></th>
            </tr>
            <#list goods as good>
                <tr>
                    <td>${good.name}</td>
                    <td><img src="data:image/jpeg;charset=utf-8;base64,${good.base64}" width="150" height="150" /></td>
                    <td>
                        <#if good.deliveryStatus>
                            delivered
                            <br/>
                            <#else>
                                not delivered
                        </#if>
                    </td>
                    <td>
                        <#if user.isAdmin>
                            <form action="changeDeliveryStatus" method="post" modelAttribute="changeDeliveryStatusForm">
                                <input type="hidden" name="id" value="${good.id}" />
                                <input class="button" type="submit" value="change status" />
                            </form>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
</body>

</html>
