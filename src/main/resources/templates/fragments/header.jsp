<html xmlns:th="http://www.thymeleaf.org">
<header th:fragment="header" xmlns:th="http://www.w3.org/1999/xhtml">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/app/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item" th:if="${isAuthenticated and !isAdministrator}">
                    <a class="nav-link" href="/app/cart/">Cart</a>
                </li>
                <li class="nav-item" th:if="${isAuthenticated and !isAdministrator}">
                    <a class="nav-link" href="/app/orders/">Orders</a>
                </li>
                <li class="nav-item" th:if="${!isAuthenticated}">
                    <a class="nav-link" href="/app/register/">Register</a>
                </li>
                <li class="nav-item" th:if="${!isAuthenticated}">
                    <a class="nav-link" href="/app/login">Login</a>
                </li>
                <li class="nav-item" th:if="${isAuthenticated}">
                    <a class="nav-link" href="/app/logout">Logout</a>
                </li>
                <li class="nav-item" th:if="${isAuthenticated and isAdministrator}">
                    <a class="nav-link" href="/app/add-product">Add Product</a>
                </li>
            </ul>
        </div>
    </nav>
</header>
