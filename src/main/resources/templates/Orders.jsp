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
            <h1 class="display-3 text-center">Your Orders</h1>
            <p class="lead text-center">This is your order history. Select an order to view the order in detail!</p>
        </div>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between" th:each="item: ${items}">
                <div class="item-details">
                    <span class="lead" th:text="'ID:' + ${item.ordersId}"></span>
                    <span class="badge badge-primary small" th:text="${item.date}"></span>
                    <span class="badge badge-dark small" th:text="${item.status}"></span>
                </div>
                <div class="btn-container">
                    <a class="btn btn-primary" th:href="@{/app/orders/} + ${item.ordersId}">View Order</a>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
