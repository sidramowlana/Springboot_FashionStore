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
            <h1 class="display-3 text-center">Your Cart</h1>
            <p class="lead text-center">This is your cart. Make changes as necessary and proceed to checkout when
                ready!</p>
        </div>
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between" th:each="item: ${carts}">
                <div class="item-details">
                    <span class="lead" th:text="${item.product.productName}"></span>
                    <span class="badge badge-primary small" th:text="${item.size}"></span>
                    <span class="badge badge-dark small" th:text="${item.quantity}"></span>
                </div>
                <div class="btn-container">
                    <form th:action="@{/app/delete-from-cart/} + ${item.cartId}" method="post">
                        <input th:name="location" th:value="cart" type="hidden">
                        <button class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </li>
        </ul>
        <a class="btn btn-primary btn-block mt-3" th:href="@{/app/checkout}">Checkout</a>
    </div>
</div>
</body>
</html>
