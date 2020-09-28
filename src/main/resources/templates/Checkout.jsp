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
            <h1 class="display-3 text-center">Checkout</h1>
            <p class="lead text-center">Enter the order details and click checkout to purchase the cart items!</p>
        </div>
        <div class="row">
            <div class="col-lg-6 col-sm-12">
                <form th:action="@{/app/checkout}" th:method="POST">
                    <div class="mb-3">
                        <span class="lead">Shipping Details</span>
                    </div>
                    <div class="form-group">
                        <label>Enter Address</label>
                        <input th:name="address" type="text" required class="form-control"
                               placeholder="No 23, John Doe Ave.">
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 form-group">
                            <label>Enter City</label>
                            <input th:name="city" type="text" required class="form-control" placeholder="Georgia">
                        </div>
                        <div class="col-md-6 form-group">
                            <label>Enter Postal Code</label>
                            <input th:name="code" type="text" required class="form-control" placeholder="11334">
                        </div>
                    </div>
                    <hr class="my-4">
                    <div class="mb-3">
                        <span class="lead">Payment Details</span>
                    </div>
                    <div class="form-group">
                        <label>Enter Card Number</label>
                        <input th:name="card" type="text" required class="form-control"
                               placeholder="3242 2342 4566 4565">
                    </div>
                    <div class="form-row">
                        <div class="col-md-6 form-group">
                            <label>Expiry Date</label>
                            <input th:name="date" type="date" required class="form-control">
                        </div>
                        <div class="col-md-6 form-group">
                            <label>CVV</label>
                            <input th:name="cvv" type="text" required class="form-control" placeholder="123">
                        </div>
                        <div class="col-md-12">
                            <button type="submit" class="btn btn-primary btn-block">Checkout</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-lg-6 col-sm-12">
                <div class="mb-3">
                    <span class="lead">Your Items</span>
                </div>
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between" th:each="item: ${items}">
                        <div class="item-details">
                            <span class="lead" th:text="${item.product.productName}"></span>
                            <span class="badge badge-primary small" th:text="${item.size}"></span>
                            <span class="badge badge-dark small" th:text="${item.quantity}"></span>
                        </div>
                        <div class="btn-container">
                            <form th:action="@{/app/delete-from-cart/} + ${item.cartId}" method="post">
                                <input th:name="location" th:value="checkout" type="hidden">
                                <button class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
