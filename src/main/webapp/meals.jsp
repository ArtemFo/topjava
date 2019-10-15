<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: green
        }

        .excess {
            color: red
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<hr>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>
                ${fn:formatDateTime(meal.dateTime)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>

    </c:forEach>
</table>
</body>
</html>
