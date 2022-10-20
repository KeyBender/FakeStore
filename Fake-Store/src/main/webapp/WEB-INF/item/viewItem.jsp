<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
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
					<li class="nav-item"><a class="nav-link" href="/aboutus">About Us</a>
					</li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#"
						id="navbarDropdownMenuLink" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> Categories </a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<li><a class="dropdown-item" href="/item/men's clothing">Men's Clothing</a></li>
							<li><a class="dropdown-item" href="/item/women's clothing">Women's Clothing</a></li>
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
							<li><a class="dropdown-item" href="/user/${navBarUserId}">Edit Profile</a></li>
							<li><a class="dropdown-item" href="/logout">Logout</a></li>
						</c:if>
						<c:if test="${!isLoggedIn }">
							<li><a class="dropdown-item" href="/signup">Sign In</a></li>						
						</c:if>
						</ul></li>
					<li class="nav-item"><a class="nav-link" href="/cart">Cart <svg
								height="30px" xmlns="http://www.w3.org/2000/svg"
								viewBox="0 0 576 512">
								<!--! Font Awesome Pro 6.2.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
								<path
									d="M24 0C10.7 0 0 10.7 0 24S10.7 48 24 48H76.1l60.3 316.5c2.2 11.3 12.1 19.5 23.6 19.5H488c13.3 0 24-10.7 24-24s-10.7-24-24-24H179.9l-9.1-48h317c14.3 0 26.9-9.5 30.8-23.3l54-192C578.3 52.3 563 32 541.8 32H122l-2.4-12.5C117.4 8.2 107.5 0 96 0H24zM176 512c26.5 0 48-21.5 48-48s-21.5-48-48-48s-48 21.5-48 48s21.5 48 48 48zm336-48c0-26.5-21.5-48-48-48s-48 21.5-48 48s21.5 48 48 48s48-21.5 48-48z" /></svg></a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid mt-4">
		<div class="row">
			<div class="col">
				<h1 class="text-center">${item.title }</h1>
				<div class="container p-4">
					<div class="row gap-5">
						<div class="row">
							<img src="${item.image }" alt=""
								style="height: 40rem; width: 40rem;" />
						</div>
						<div class="row">
							<h3>${item.description }</h3>
						</div>
						<div class="row">
							<c:if test="${item.onSale}">
								<h4>
									$<span style="text-decoration: line-through;">${item.price }</span>
								</h4>
								<h4 class="text-danger">$${item.salePrice }</h4>
							</c:if>
							<c:if test="${!item.onSale}">
								<h4>$${item.price }</h4>
							</c:if>
						</div>
						<div>
							<a href="/cart/${item.id }" class="btn btn-success">Add to Cart</a>
						</div>
					</div>
				</div>

			</div>
			<div class="col col-sm-3">
				<c:forEach var="item" items="${categoryItems }">
					<a href="/item/view/${item.id }" style="text-decoration: none;"
						class="text-dark">
						<div class="card m-3 shadow p-1 bg-white-rounded">
							<img src="${item.image }" class="card-img-top m-auto"
								alt="card-grid-image" style="height: 15rem; width: 15rem;">
							<div class="card-body">
								<h5 class="card-title">${item.title }</h5>
								<p class="card-text">${item.description }</p>
								<c:if test="${item.onSale}">
									<p>
										$<span style="text-decoration: line-through;">${item.price }</span>
									</p>
									<p class="text-danger">$${item.salePrice }</p>
								</c:if>
								<c:if test="${!item.onSale}">
									<p>$${item.price }</p>
								</c:if>
							</div>
						</div>
					</a>
				</c:forEach>
			</div>
		</div>

	</div>
</body>
</html>