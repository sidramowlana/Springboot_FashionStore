<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Our Products</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" th:href="@{/styles/Cart.css}"/>
    <link rel="stylesheet" th:href="@{/styles/Root.css}"/>
</head>
<body>
<div th:replace="fragments/header :: header"></div>
<div class="root-div">
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-3 text-center" th:text="'Order ID: ' + ${item.ordersId}"></h1>
            <p class="lead text-center" th:text="'Date: ' + ${item.date} + ' | ' + 'Status: ' + ${item.status}"></p>
        </div>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between" th:each="item: ${items}">
                <div class="item-details">
                    <span class="lead" th:text="${item.product.productName}"></span>
                    <span class="badge badge-primary small" th:text="${item.size}"></span>
                    <span class="badge badge-dark small" th:text="${item.quantity}"></span>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
