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
            <h1 class="display-3 text-center">Add Product</h1>
            <p class="lead text-center">Hey there, let's add a product!</p>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <form th:action="@{/app/add-product}" th:method="POST">
                    <div class="form-row">
                        <div class="col-md-6 form-group">
                            <label>Enter Product Name</label>
                            <input th:name="name" type="text" required class="form-control"
                                   placeholder="Sample T-Shirt">
                        </div>
                        <div class="col-md-6 form-group">
                            <label>Enter Category</label>
                            <input th:name="category" type="text" required class="form-control" placeholder="T-Shirts">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Enter Price</label>
                        <input th:name="price" type="number" required class="form-control" placeholder="3.99">
                    </div>
                    <div class="form-group">
                        <label>Enter Quantity</label>
                        <input th:name="quantity" type="number" required class="form-control" placeholder="100">
                    </div>
                    <div class="form-group">
                        <label>Enter Image URL</label>
                        <input th:name="image" type="text" required class="form-control"
                               placeholder="http://sample.org/sample.png">
                    </div>
                    <div class="form-group">
                        <label>Enter Short Description</label>
                        <input th:name="description" type="text" required class="form-control"
                               placeholder="This is a sample T-Shirt">
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Add Product</button>
                    <div class="alert alert-danger mt-3 alert-block text-center" th:if="${error != null}"
                         th:text="${error}"></div>
                    <div class="alert alert-success mt-3 alert-block text-center" th:if="${success != null}"
                         th:text="${success}"></div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
