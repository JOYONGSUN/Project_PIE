<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파이</title>
<jsp:include page="/resources/static/static.jsp"></jsp:include>
<script src="<c:url value="/resources/js/googleLogin.js"/>"></script>
</head>
<body>
	<!-- 이메일 인증 성공 or 실패 alert -->
	<c:if test="${sessionScope.trueOrFalse == true}">
		<script>
				alert("이메일 인증이 완료되었습니다.");
			</script>
	</c:if>
	<c:if test="${sessionScope.trueOrFalse == false}">
		<script>
				alert("이메일 인증이 실패하였습니다.");
			</script>
	</c:if>
	<c:if test="${sessionScope.check}">
		<script>
				alert("비밀번호가 변경되었습니다.");
				${sessionScope.check = false}
		</script>
	</c:if>

	<!-- 회원가입 포맷 -->
	<form action="${pageContext.request.contextPath}/login" method="post">
		<input type="text" name="username" placeholder="이메일"><br>
		<input type="password" name="password" placeholder="비밀번호"><br>
		<input type="submit" value="로그인"> <a href="join.do"><input
			type="button" value="회원가입"></a> <a href="findPassword.do"><input
			type="button" value="비밀번호 찾기"></a>
	</form>

	<c:if test="${param.error != null }">
		<div>
			<c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
				<c:out value="이메일 또는 비밀번호가 일치하지 않습니다. 다시 확인해주세요." />
			</c:if>
		</div>
	</c:if>
	
	<!-- 네이버 로그인 -->
	<a href="naverlogin.do">
	<img width="120" height="36" src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png" />
	</a>
	
	<!-- 구글 로그인 -->
	<div id = "google" class="g-signin2" data-onsuccess = "onSignIn"></div>
	
	<a href="chatWebSocket.htm">채팅로그인</a>
</body>
</html>