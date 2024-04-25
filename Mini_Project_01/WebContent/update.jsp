<%@ page import="javax.servlet.http.*" %>
<%@page import="bean.UserDAO"%>
<%@page import="bean.UserVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <% 
        session = request.getSession(false);
       	UserVO vo = new UserDAO().findUser((Integer)session.getAttribute("userSeq"));
    %>

	<h1>비밀번호 변경</h1>
	<form action="updateAction" method="post">
		<input type="hidden" name="userId" value="<%= vo.getUserId() %>">
		비밀번호 입력 : <input name="userPwInput" type="password"><br>
		비밀번호 확인 : <input name="userPwCheck" type="password"><br>
		<input type="submit" value="수정">
		<input type="button" value="취소" onclick="location.href='login.jsp'">
	</form>
</body>
</html>