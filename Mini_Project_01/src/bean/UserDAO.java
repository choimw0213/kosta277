package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private final Connection connection;
	
	public UserDAO() throws ClassNotFoundException, SQLException{
		connection = DBManager.getInstance().getConnection();
	}
	
	public int createUser(String userId, String userPw, String userName, String userGender, String userPhone){
		String sql = null;
		sql = "select user_id from user_list where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) return -1; // 아이디 중복
			
			sql = "insert into user_list values (user_seq.nextval, ?, ?, ?, ?, ?, sysdate)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			pstmt.setString(3, userName);
			pstmt.setString(4, userGender);
			pstmt.setString(5, userPhone);
			if(pstmt.executeUpdate() != 1){
				return -2; // DB실패
			}
			
			sql = "select user_seq from user_list where user_id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) return rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int login(String userId, String userPw) {
		String sql = null;
		sql = "select user_seq, user_id, user_pw, user_name, user_gender, user_phone, user_date "
				+ "from user_list where user_id = ?";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) return -1; // 아이디 없음
			
			if(!userPw.equals(rs.getString(3))){
				return -2; // 비밀번호 불일치
			} else {
				return rs.getInt(1); // 비밀번호 일치
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int updateUserPw(String userId, String userPw) {
		String sql = null;
		sql = "update user_list set user_pw = ? where user_id = ?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userPw);
			pstmt.setString(2, userId);
			if(pstmt.executeUpdate() != 1) return -1; // 수정 실패
			else return 1; // 수정 성공
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public UserVO findUser(int userSeq){
		UserVO vo = null;
		String sql = null;
		sql = "select user_seq, user_id, user_pw, user_name, user_gender, user_phone, user_date "
				+ "from user_list where user_seq = ?";
		
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, userSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				vo = new UserVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return vo;
	}
}





