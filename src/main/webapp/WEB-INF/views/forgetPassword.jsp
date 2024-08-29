<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- directive của JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<title>Nhà sách trực tuyến Fahasa.com</title>
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

    <!--  -->
    <nav>

    </nav>

    <!--  -->
    <main>
        <div class="container">
            <div class="login_title">
                <h3>Quên mật khẩu</h3>
            </div>
            <div class="login_form">
                <form class="form_creat_user" method="POST" action="${classpath }/register">
                    <div class="login_row">
                        <label for="">Email</label>
                        <input type="text" name="username" id="username" placeholder="Nhập số điện thoại hoặc email">
                    </div>
                    <div class="login_button">
                        <button type="submit">Kiểm tra</button>
                    </div>
                    
                </form>
            </div>
        </div>
    </main>

    <!-- footer -->
    
</body>
</html>