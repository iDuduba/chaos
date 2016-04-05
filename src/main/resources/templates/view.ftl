<html>
<head>
    <title>用户信息</title>
    <link href="${request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body style="margin-top:50px;overflow: hidden;">
<form action="${request.contextPath}/users/save" method="post">
    <input type="hidden" name="id" value="<#if user.id??>${user.id}</#if>"/>
    <table class="gridtable" style="width:800px;">
        <tr>
            <th colspan="5">用户信息 - [<a href="${request.contextPath}/users">返回</a>]</th>
        </tr>
        <tr>
            <th>用户名称：</th>
            <td><input type="text" name="username" value="<#if user.username??>${user.username}</#if>"/>
            </td>
            <th>用户口令：</th>
            <td><input type="text" name="password" value="<#if user.password??>${user.password}</#if>"/>
            </td>
            <td><input type="submit" value="保存"/></td>
        </tr>
    <#if msg??>
        <tr style="color:#00ba00;">
            <th colspan="5">${msg}</th>
        </tr>
    </#if>
    </table>
</form>
</body>
</html>
