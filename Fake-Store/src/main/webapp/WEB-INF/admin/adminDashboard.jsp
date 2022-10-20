<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FakeStore</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-xl navbar-light bg-primary">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">Fake Store</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarBasic"
				aria-controls="navbarBasic" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse show" id="navbarBasic">
				<ul class="navbar-nav me-auto mb-2 mb-xl-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/">On Sale</a></li>
					<li class="nav-item"><a class="nav-link" href="/aboutus">About
							Us</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Categories </a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item" href="/item/men's clothing">Men's
									Clothing</a></li>
							<li><a class="dropdown-item" href="/item/women's clothing">Women's
									Clothing</a></li>
							<li><a class="dropdown-item" href="/item/jewelery">Jewelery</a></li>
							<li><a class="dropdown-item" href="/item/electronics">Electronics</a></li>
						</ul></li>
				</ul>
				<form class="d-flex" action="/item/search">
					<input class="form-control me-2" type="search" placeholder="Search"
						aria-label="Search" name="name">
					<button class="btn btn-success" type="submit">Search</button>
				</form>
				<ul class="navbar-nav ms-auto mb-2 mb-xl-0">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Account </a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<c:if test="${isLoggedIn }">
								<li><a class="dropdown-item" href="/user/${navBarUserId}">Edit
										Profile</a></li>
								<li><a class="dropdown-item" href="/logout">Logout</a></li>
							</c:if>
							<c:if test="${!isLoggedIn }">
								<li><a class="dropdown-item" href="/signup">Sign In</a></li>
							</c:if>
						</ul></li>
					<li class="nav-item"><a class="nav-link" href="/cart">Cart
							<svg height="30px" xmlns="http://www.w3.org/2000/svg"
								viewBox="0 0 576 512">
								<!--! Font Awesome Pro 6.2.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
								<path
									d="M24 0C10.7 0 0 10.7 0 24S10.7 48 24 48H76.1l60.3 316.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24-10.7 24-24s-10.7-24-24-24H179.9l-9.1-48h317c14.3 0 26.9-9.5 30.8-23.3l54-192C578.3 52.3 563 32 541.8 32H122l-2.4-12.5C117.4 8.2 107.5 0 96 0H24zM176 512c26.5 0 48-21.5 48-48s-21.5-48-48-48s-48 21.5-48 48s21.5 48 48 48zm336-48c0-26.5-21.5-48-48-48s-48 21.5-48 48s21.5 48 48 48s48-21.5 48-48z" /></svg>
					</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid m-4">
		<div class="row">
			<div class="col"></div>
			<div class="col col-xl-8">
				<div class=" container-fluid d-flex justify-content-between">
					<h4>
						<a href="#users">Users</a>
					</h4>
					<h4>
						<a href="#orders">Orders</a>
					</h4>
					<h4>
						<a href="#items">Items</a>
					</h4>
				</div>

				<div class="mt-4">
					<h4 class="text-center">Users</h4>
					<table id="users" class="table table-hover">
						<thead>
							<td>Name</td>
							<td>Email</td>
							<td class="text-end">Is Admin</td>
						</thead>
						<tbody>
							<c:forEach var="user" items="${allUsers }">
								<tr>
									<td><a href="/admin/user/${user.id }">${user.firstName }
											${user.lastName }</a></td>
									<td>${user.email }</td>
									<td class="text-end"><c:if test="${user.isAdmin }">
											<span style="color: green;">Yes</span>
										</c:if> <c:if test="${!user.isAdmin }">
											<span style="color: red;">No</span>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div id="orders" class="mt-4">
					<h4 class="text-center">Orders</h4>
					<table class="table table-hover mt-3">
						<thead>
							<td>Order #</td>
							<td class="text-end">Is Shipped</td>
						</thead>
						<tbody>
							<c:forEach var="order" items="${unShippedOrders }">
								<tr>
									<td><a href="/admin/order/${order.id }">${order.id}</a></td>
									<td class="text-end"><span style="color: red;">No</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table class="table table-hover mt-3">
						<thead>
							<td>Order #</td>
							<td class="text-end">Is Shipped</td>
						</thead>
						<tbody>
							<c:forEach var="order" items="${shippedOrders }">
								<tr>
									<td><a href="/admin/order/${order.id }">${order.id}</a></td>
									<td class="text-end"><span style="color: green;">Yes</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div id="items" class="mt-4">
					<h4 class="text-center">Items</h4>
					<a href="/admin/item" class="btn btn-success">+ Add New Item</a>
					<table class="table table-hover">
						<thead>
							<td>Item Name</td>
							<td>Item #</td>
							<td>Price</td>
							<td class="text-end">On Sale</td>
						</thead>
						<tbody>
							<c:forEach var="item" items="${allItems }">
								<tr>
									<td><a href="/admin/item/${item.id }">${item.title }</a></td>
									<td>${item.id }</td>
									<td>$${item.price }</td>
									<td class="text-end"><c:if test="${item.onSale }">
											<span style="color: green;">Yes</span>
										</c:if> <c:if test="${!item.onSale }">
											<span style="color: red;">No</span>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="col"></div>
		</div>
	</div>
</body>
</html>