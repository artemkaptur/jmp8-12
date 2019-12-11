<#import "/spring.ftl" as spring/>
<html>

<head>
    <title>Purchase</title>
</head>

<body>
    <#include "header.ftl">
        <br/>
        <br/>
        <#if user.isAdmin>
            <table>
                <tr>
                    <th>name</th>
                    <th>image</th>
                    <th>update</th>
                    <th>delete</th>
                </tr>
                <#list goods as oneGood>
                    <tr>
                        <td>${oneGood.name}</td>
                        <td><img src="data:image/jpeg;charset=utf-8;base64,${oneGood.base64}" width="150" height="150" /></td>
                        <td>
                            <form action="/updateGood" method="post" enctype="multipart/form-data">
                                <@spring.bind path="updateGoodForm" />
                                <input type="hidden" id="id" name="id" value="${oneGood.id}"> input new good name
                                <@spring.formInput "updateGoodForm.name"/> input new image
                                <input type="file" name="file" />
                                <input class="button" type="submit" value="update good" />
                            </form>
                        </td>
                        <td>
                            <form action="/deleteGood" method="post">
                                <@spring.bind path="deleteGoodForm" />
                                <input type="hidden" id="id" name="id" value="${oneGood.id}">
                                <input class="button" type="submit" value="delete" />
                            </form>
                        </td>
                    </tr>
                </#list>
            </table>
            <form action="/addGood" method="post" enctype="multipart/form-data">
                input new good name
                <input type="text" name="name" id="name" /> input new image
                <input type="file" name="file" />
                <input class="button" type="submit" value="add good" />
            </form>
            <#else>
                <form action="/addPurchase" method="post">
                    <table>
                        <tr>
                            <th>name</th>
                            <th>image</th>
                            <th>create purchase</th>
                        </tr>
                        <#list goods as good>
                            <tr>
                                <td>${good.name}</td>
                                <td><img src="data:image/jpeg;charset=utf-8;base64,${good.base64}" width="150" height="150" /></td>
                                <td>
                                    <input type="checkbox" name="idGoods" value="${good.id}" />
                                </td>
                            </tr>
                        </#list>
                    </table>
                    <input class="button" type="submit" value="create purchase" />
                </form>
        </#if>
        <ul class="pagination pagination-sm">
            <li class="page-item"><a class="page-link" href="/purchase/1">1</a></li>
            <li class="page-item"><a class="page-link" href="/purchase/2">2</a></li>
            <li class="page-item"><a class="page-link" href="/purchase/3">3</a></li>
        </ul>
</body>

</html>
