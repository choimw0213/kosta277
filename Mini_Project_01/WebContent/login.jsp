<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="bean.UserVO" %>
<%@ page import="bean.UserDAO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <% 
        session = request.getSession(false);
        if(session != null && session.getAttribute("userSeq") != null) {
        	UserVO vo = new UserDAO().findUser((Integer)session.getAttribute("userSeq"));
    %>
       	아이디 : <%= vo.getUserId() %><br>
   		이름 : <%= vo.getUserName() %><br>
   		성별 : <%= vo.getUserGender() %><br>
   		전화번호 : <%= vo.getUserPhone() %><br>
   		가입날짜 : <%= vo.getUserDate() %><br>
        <input type="button" value="수정" onclick="location.href='update.jsp'"><input type="button" value="로그아웃" onclick="location.href='logoutAction'">
    <% } else { %>
		<h1>로그인</h1>
		<form action="loginAction" method="post">
			아이디 : <input name="userId"><br>
			비밀번호 : <input name="userPw" type="password"><br>
			<input type="submit" value="로그인"><br>
			<input type="button" value="회원가입" onclick="location.href='join.jsp'">
		</form>
    <% } %>
</body>
</html>