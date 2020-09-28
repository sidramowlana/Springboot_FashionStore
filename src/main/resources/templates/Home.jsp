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
    <link rel="stylesheet" th:href="@{/styles/Home.css}"/>
</head>
<body>
<div>
    <div th:replace="fragments/header :: header"></div>
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-3 text-center">Our Products</h1>
            <p class="lead text-center">Welcome to FashionStore, the leading E-Commerce store for your favourite
                brand!</p>
        </div>
        <div class="product-container">
            <div th:each="product: ${products}" class="product-card">
                <div class="card">
                    <img class="card-img-top" th:src="${product.scaledImage}" alt="${product.productName}"/>
                    <div class="card-body">
                        <h5 class="card-title" th:text="${product.productName}"></h5>
                        <p class="card-text" th:text="${product.price}"></p>
                        <div class="btn-group" th:if="${isAuthenticated and !isAdministrator}">
                            <a th:href="@{/app/product/} + ${product.productId}" th:type="button"
                               class="btn btn-primary">View Product</a>
                            <form th:action="@{/app/add-to-cart/} + ${product.productId}" th:method="post">
                                <input type="hidden" th:value="1" id="quantity" th:name="quantity" class="form-control">
                                <button th:type="submit" class="btn btn-secondary">Add to Cart</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
