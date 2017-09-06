<%--
  Created by IntelliJ IDEA.
  User: abhishek.sing
  Date: 06/09/17
  Time: 12:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pro kabaddi - days</title>
</head>
<body>
<form  method="post" action="/rest/scheduler/getDays">
    Number of Teams <input name="numTeams", type="number" id="numTeams"> </input>
    <input type="submit" value="Get Schedule"/>
</body>
</html>
