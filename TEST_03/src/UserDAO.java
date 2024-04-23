import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private final Connection connection;

	public UserDAO() throws ClassNotFoundException, SQLException{
		connection = DBManager.getInstance().getConnection();
	}

	//회원가입
	public boolean createUser(String userId, String userPw, String name, String phoneNumber, String nickname){
		if(isValidUserId(userId) && isValidUserPw(userPw) && isValidName(name) && isValidPhoneNumber(phoneNumber) && isValidNickname(nickname)){
			return createUser(new UserVO(userId, userPw, name, phoneNumber, nickname));			
		}
		return false;
	}

	public boolean createUser(UserVO vo){
		String sql = null;

		sql = "select user_id from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getUserId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("이미 존재하는 아이디입니다.");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql = "select user_id from users where phone_number = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getPhoneNumber());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("이미 존재하는 전화번호입니다.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "select user_id from users where nickname = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getNickname());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("이미 존재하는 닉네임입니다.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "insert into users values(?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getUserId());
			pstmt.setString(2, vo.getUserPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhoneNumber());
			pstmt.setString(5, vo.getNickname());
			int num = pstmt.executeUpdate();
			if(num == 1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//마이페이지(내 정보)
	public UserVO readUser(String userId){
		UserVO vo = null;
		String sql = null;

		sql = "select user_id, user_pw, name, phone_number, nickname from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				vo = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	//마이페이지(회원정보 수정)
	public boolean updateUser(String userId, String userPw, String name, String phoneNumber, String nickname){
		if(!(isValidUserId(userId) && isValidUserPw(userPw) && isValidName(name) && isValidPhoneNumber(phoneNumber) && isValidNickname(nickname))){
			return false;
		}

		String sql = null;

		sql = "select user_id from users where phone_number = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, phoneNumber);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() && !rs.getString(1).equals(userId)){
				System.out.println("이미 존재하는 전화번호 입니다.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "select user_id from users where nickname = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nickname);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() && !rs.getString(1).equals(userId)) {
				System.out.println("이미 존재하는 닉네임 입니다.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "update users "
				+ "set user_pw = ?, "
				+ "name = ?, "
				+ "phone_number = ?, "
				+ "nickname = ? "
				+ "where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userPw);
			pstmt.setString(2, name);
			pstmt.setString(3, phoneNumber);
			pstmt.setString(4, nickname);
			pstmt.setString(5, userId);
			int num = pstmt.executeUpdate();
			if(num == 1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//회원탈퇴
	public boolean deleteUser(String userId, String userPw, String name, String phoneNumber, String nickname){
		String sql = null;

		sql = "select user_id, user_pw, name, phone_number, nickname from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(2).equals(userPw)){
					if(rs.getString(3).equals(name)){
						if(rs.getString(4).equals(phoneNumber)){
							if(rs.getString(5).equals(nickname)){
								return deleteUser(new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
							} else {
								System.out.println("닉네임이 일치하지 않습니다.");
							}
						} else {
							System.out.println("전화번호가 일치하지 않습니다.");
						}
					} else {
						System.out.println("이름이 일치하지 않습니다.");
					}
				} else {
					System.out.println("패스워드가 일치하지 않습니다.");
				}
			} else {
				System.out.println("아이디가 존재하지 않습니다");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteUser(UserVO vo){
		String sql1 = "delete from comment_likes where comment_seq in (select comment_seq from comments where user_id = ?)";
		String sql2 = "delete from comment_likes where user_id = ?";
		String sql3 = "delete from comments where user_id = ?";

		String sql4 = "delete from comment_likes where comment_seq in (select comment_seq from comments where board_seq in (select board_seq from boards where user_id = ?))";
		String sql5 = "delete from comments where board_seq in (select board_seq from boards where user_id = ?)";
		String sql6 = "delete from board_likes where board_seq in (select board_seq from boards where user_id = ?)";
		String sql7 = "delete from board_likes where user_id = ?";		
		String sql8 = "delete from boards where user_id = ?";

		String sql9 = "delete from users where user_id = ?";

		try {
			PreparedStatement pstmt = null;

			pstmt = connection.prepareStatement(sql1);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql2);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql3);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql4);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql5);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql6);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql7);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql8);
			pstmt.setString(1, vo.getUserId());
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql9);
			pstmt.setString(1, vo.getUserId());
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//로그인
	public boolean logIn(String userId, String userPw){
		String sql = null;

		sql = "select user_pw from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				if(userPw.equals(rs.getString(1))){
					System.out.println("로그인 성공");
					return true;
				} else {
					System.out.println("패스워드가 일치하지 않습니다.");
				}
			}
			else {
				System.out.println("아이디가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//ID찾기
	public String getUserId(String name, String phoneNumber){
		String userId = null;
		String sql = null;

		sql = "select user_id, name from users where phone_number = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, phoneNumber);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(2).equals(name)){
					userId = rs.getString(1);
				} else {
					System.out.println("이름이 일치하지 않습니다.");
				}
			} else {
				System.out.println("아이디가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
	//PW찾기
	public String getUserPw(String userId, String name, String phoneNumber){
		String userPw = null;
		String sql = null;

		sql = "select user_pw, name, phone_number from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString(2).equals(name)){
					if(rs.getString(3).equals(phoneNumber)){
						userPw = rs.getString(1);
					} else {
						System.out.println("전화번호가 일치하지 않습니다.");	
					}
				} else {
					System.out.println("이름이 일치하지 않습니다.");
				}
			} else {
				System.out.println("아이디가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userPw;
	}

	public boolean isValidNickname(String nickname) {
		return isValidUserId(nickname);
	}

	public boolean isValidPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() != 11) {
			System.out.println("11자여야 합니다.");
			return false;
		}
		if (!phoneNumber.startsWith("010")) {
			System.out.println("010으로 시작해야 합니다.");
			return false;
		}
		for (char ch : phoneNumber.toCharArray()) {
			if (!Character.isDigit(ch)) {
				System.out.println("숫자만 입력 가능합니다.");
				return false;
			}
		}
		return true;
	}

	public boolean isValidName(String name) {
		if(name.length() < 2 || name.length() > 20){
			System.out.println("최소 2자 ~ 최대 20자까지 입력 가능합니다.");
			return false;
		}

		for (char ch : name.toCharArray()) {
			if(!Character.isLetter(ch)){
				System.out.println("문자만 입력 가능합니다.");
				return false;
			}
		}
		return true;
	}

	public boolean isValidUserPw(String userPw) {
		if (userPw.length() < 8 || userPw.length() > 20) {
			System.out.println("최소 8자 ~ 최대 20자까지 입력 가능합니다.");
			return false;
		}

		boolean hasUpperCase = false;
		boolean hasLowerCase = false;
		boolean hasDigit = false;
		boolean hasSpecialChar = false;
		for (char ch : userPw.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				hasUpperCase = true;
			} else if (Character.isLowerCase(ch)) {
				hasLowerCase = true;
			} else if (Character.isDigit(ch)) {
				hasDigit = true;
			} else {
				// 특수 문자 중 공백을 제외한 나머지를 허용하지 않음
				if (ch == ' ' || ch == '\'' || ch == '\"' || ch == '\\') {
					System.out.println("공백, 작은 따옴표, 큰 따옴표, 백슬래시는 입력할 수 없습니다.");
					return false;
				}
				hasSpecialChar = true;
			}
		}

		int requiredTypes = 0;
		if (hasUpperCase) requiredTypes++;
		if (hasLowerCase) requiredTypes++;
		if (hasDigit) requiredTypes++;
		if (hasSpecialChar) requiredTypes++;

		if(requiredTypes >= 2) return true;
		else {
			System.out.println("대문자, 소문자, 숫자, 특수문자 중 2가지 이상 입력되어야 합니다.");
			return false;
		}
	}

	public boolean isValidUserId(String userId) {
		if(userId.length() < 4 || userId.length() > 20){
			System.out.println("최소 4자 ~ 최대 20자까지 입력 가능합니다.");
			return false;
		}

		if (Character.isDigit(userId.charAt(0))) {
			System.out.println("첫 글자로 숫자를 입력할 수 없습니다.");
			return false;
		}

		for (char ch : userId.toCharArray()) {
			if(!Character.isLetterOrDigit(ch)){
				System.out.println("문자와 숫자만 입력 가능합니다.");
				return false;
			}
		}
		return true;
	}
}
