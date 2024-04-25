<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<form action="joinAction" method="post">
		아이디 : <input name="userId"><br>
		비밀번호 입력 : <input name="userPw" type="password"><br>
		비밀번호 확인 : <input type="password"><br>
		이름 : <input name="userName"><br>
		성별 : 
			남자<input type="radio" name="userGender" value='M'>
			여자<input type="radio" name="userGender" value='F'><br>
		전화번호 : <input name="userPhone"><br>
		<input type="submit" value="확인"><input type="button" value="취소" onclick="location.href='login.jsp'">
	</form>
</body>
</html>