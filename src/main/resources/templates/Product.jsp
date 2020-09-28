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
    <link rel="stylesheet" th:href="@{/styles/Product.css}"/>
    <link rel="stylesheet" th:href="@{/styles/Root.css}"/>
</head>
<body>
<div th:replace="fragments/header :: header"></div>
<div class="root-div">
    <div class="product-container">
        <div class="actual-container row">
            <img class="img-fluid" th:src="${product.scaledImage}" alt="${product.productName}"/>
            <div class="details-container col py-4">
                <div class="jumbotron p-4 h-100">
                    <h1 class="display-4" th:text="${product.productName}"></h1>
                    <p class="lead" th:text="${product.shortDescription}"></p>
                    <p class="display-3 text-danger" th:text="${'$' + product.price}"></p>
                </div>
                <form method="post" class="product-form" th:action="@{/app/add-to-cart/} + ${product.productId}">
                    <input type="hidden" th:value="1" id="userId" th:name="userId">
                    <div class="form-row">
                        <div class="col-10">
                            <input type="number" th:value="1" id="quantity" th:name="quantity" class="form-control">
                        </div>
                        <div class="col-2">
                            <button th:disabled="${!isAuthenticated}" th:type="submit"
                                    class="btn btn-primary btn-block">Add to Cart
                            </button>
                        </div>
                    </div>
                </form>
                <form method="post" class="review-form mt-2" th:action="@{/app/add-review/} + ${product.productId}">
                    <div class="form-row">
                        <div class="col-12 col-lg-8">
                            <input type="text" placeholder="Enter product feedback." th:name="feedback" required
                                   id="feedback" class="form-control">
                        </div>
                        <div class="col-12 col-lg-2">
                            <input type="number" th:value="1" th:max="5" th:min="1" th:name="rating" required
                                   id="rating" class="form-control">
                        </div>
                        <div class="col-12 col-lg-2">
                            <button type="submit" th:disabled="${!isAuthenticated}" class="btn btn-dark btn-block">Add
                                Review
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm-12">
                <div class="jumbotron w-100">
                    <h1 class="display-4 text-center" th:text="${'Reviews for ' + product.productName}"></h1>
                </div>
                <ul class="list-group w-100">
                    <li class="list-group-item d-flex justify-content-between" th:each="item: ${reviews}">
                        <span class="lead" th:text="${item.feedback}"></span>
                        <span class=" px-4 py-2 badge badge-light small" th:text="${'By ' + item.user.username}"></span>
                        <span class="ml-auto px-4 py-2 badge badge-primary small" th:text="${item.rate}"></span>
                        <span class="ml-2 px-4 py-2 badge badge-dark small" th:text="${item.date}"></span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
