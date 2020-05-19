<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage = "true"%>
<%--
	isErrorPage 지시어
	: 현재 JSP페이지가 에러처리를 담당하는지 구분하는 속성으로
	false가 디폴트값이다. 에러처리를 위해서 true로 설정해야 한다.
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ErrorPage.jsp</title>
</head>
<body>
	<h2>에러발생됨 : 관리자에게 문의하세요</h2>
	<h3>연락처 : 010-1234-5678</h3>
	<h3>서버 업그레이드 중입니다.</h3>
	<img src="data:image/jpeg;base64,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"/>
	<h3>
		에러내용 : <%=exception.getMessage() %>
	</h3>
</body>
</html>