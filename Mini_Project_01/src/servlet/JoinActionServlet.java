package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserDAO;

@WebServlet("/joinAction")
public class JoinActionServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userPhone = request.getParameter("userPhone");
		
		String url = "join.jsp";
		try {
			if(new UserDAO().createUser(userId, userPw, userName, userGender, userPhone) > 0){
				url = "login.jsp"; // 회원가입 성공
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/"+url).forward(request, response);
		
	}
}
