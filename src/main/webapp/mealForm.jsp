<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>Meal</h3>
<hr>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" size="40" name="description" value="${meal.description}"></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" value="${meal.calories}"></dd>
    </dl>
    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>

</body>
</html>
