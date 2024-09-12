<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- directive của JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<title>Signup</title>
<link rel="icon"
	href="${classpath}/frontend/images/header_img/favicon4.ico"
	type="image/x-icon">
<meta name='viewport' content='width=device-width, initial-scale=1'>

<link rel='stylesheet' type='text/css' media='screen'
	href='${classpath}/frontend/css/style4.css'>
<link rel="stylesheet" href="${classpath}/frontend/slick/slick.css">
<link rel="stylesheet"
	href="${classpath}/frontend/font/SansSerifBldFLF-Italic.otf">
<link rel="stylesheet" href="${classpath}/backend/css/bootstrap.min.css" />
<!-- BoxIcon -->
<link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css'
	rel='stylesheet'>
<!-- Swiper -->
<link rel="stylesheet"
	href="https://unpkg.com/swiper/swiper-bundle.min.css" />

	<style>
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .alert-danger {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
        .alert-success {
            color: #3c763d;
            background-color: #dff0d8;
            border-color: #d6e9c6;
        }
    </style>

</head>
<body>

<!-- header -->
	<div class="header_user">
                        <c:choose>
					        <c:when test="${loginedUser.username != null}">
					            <a href="${classpath }/logout">
					                <img src="${classpath}/frontend/images/header_img/ico_account_gray.svg" alt="">
					            </a>
					            <div class="top_menu_label">${loginedUser.username}</div>
					        </c:when>
					        <c:otherwise>
					            <a href="${classpath }/login">
					                <img src="${classpath}/frontend/images/header_img/ico_account_gray.svg" alt="">
					            </a>
					            <div class="top_menu_label">Đăng nhập</div>
					        </c:otherwise>
					    </c:choose>
                    </div>
	
	<nav></nav>

    <!--  -->
    <nav>

    </nav>

    <!--  -->
    <main>
        <div class="container">
            <div class="login_title">
                <h3>Đổi mật khẩu</h3>
            </div>
            <div class="login_form">
                <form class="form_creat_user" method="POST" action="${classpath }/change-password" >
                    <div class="login_row">
                        <label for="">Email</label>
                        <input type="text" name="email" id="email" value="${loginedUser.username}" placeholder="${loginedUser.username}" readonly>
                    </div>
                    <div class="login_row">
                        <label for="">Mật khẩu cũ</label>
                        <input type="password" name="password" id="password" placeholder="Nhập mật khẩu">
                    </div>
                    <div class="login_row">
                        <label for="retypepassword">Mật khẩu mới</label>
                        <input type="password" name="newPassword" id="newPassword" placeholder="Nhập lại mật khẩu">
                    </div>
                    <div class="login_row">
                        <label for="retypepassword">Nhập lại mật khẩu mới</label>
                        <input type="password" name="retypePassword" id="retypePassword" placeholder="Nhập lại mật khẩu">
                    </div>
                    <div class="password_forget">
                        <a href="${classpath }/login">Đăng nhập</a>
                    </div>
                    <div class="login_button">
                        <button type="submit">Cập nhật mật khẩu</button>
                    </div>
                    
                </form>
            </div>
        </div>
    </main>
    
    <!-- Hiển thị thông báo lỗi -->
    			
    			
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        <c:out value="${error}" />
                    </div>
                </c:if>
                
                <!-- Hiển thị thông báo thành công -->
                <c:if test="${not empty success}">
                    <div class="alert alert-success" role="alert">
                        <c:out value="${success}" />
                    </div>
                </c:if>

</body>
</html>