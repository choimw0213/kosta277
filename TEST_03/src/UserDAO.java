import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private final Connection connection;

	public UserDAO() throws ClassNotFoundException, SQLException{
		connection = DBManager.getInstance().getConnection();
	}

	//ȸ������
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
				System.out.println("�̹� �����ϴ� ���̵��Դϴ�.");
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
				System.out.println("�̹� �����ϴ� ��ȭ��ȣ�Դϴ�.");
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
				System.out.println("�̹� �����ϴ� �г����Դϴ�.");
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
	//����������(�� ����)
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
	//����������(ȸ������ ����)
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
				System.out.println("�̹� �����ϴ� ��ȭ��ȣ �Դϴ�.");
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
				System.out.println("�̹� �����ϴ� �г��� �Դϴ�.");
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
	//ȸ��Ż��
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
								System.out.println("�г����� ��ġ���� �ʽ��ϴ�.");
							}
						} else {
							System.out.println("��ȭ��ȣ�� ��ġ���� �ʽ��ϴ�.");
						}
					} else {
						System.out.println("�̸��� ��ġ���� �ʽ��ϴ�.");
					}
				} else {
					System.out.println("�н����尡 ��ġ���� �ʽ��ϴ�.");
				}
			} else {
				System.out.println("���̵� �������� �ʽ��ϴ�");
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
	//�α���
	public boolean logIn(String userId, String userPw){
		String sql = null;

		sql = "select user_pw from users where user_id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				if(userPw.equals(rs.getString(1))){
					System.out.println("�α��� ����");
					return true;
				} else {
					System.out.println("�н����尡 ��ġ���� �ʽ��ϴ�.");
				}
			}
			else {
				System.out.println("���̵� �������� �ʽ��ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	//IDã��
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
					System.out.println("�̸��� ��ġ���� �ʽ��ϴ�.");
				}
			} else {
				System.out.println("���̵� �������� �ʽ��ϴ�.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userId;
	}
	//PWã��
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
						System.out.println("��ȭ��ȣ�� ��ġ���� �ʽ��ϴ�.");	
					}
				} else {
					System.out.println("�̸��� ��ġ���� �ʽ��ϴ�.");
				}
			} else {
				System.out.println("���̵� �������� �ʽ��ϴ�.");
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
			System.out.println("11�ڿ��� �մϴ�.");
			return false;
		}
		if (!phoneNumber.startsWith("010")) {
			System.out.println("010���� �����ؾ� �մϴ�.");
			return false;
		}
		for (char ch : phoneNumber.toCharArray()) {
			if (!Character.isDigit(ch)) {
				System.out.println("���ڸ� �Է� �����մϴ�.");
				return false;
			}
		}
		return true;
	}

	public boolean isValidName(String name) {
		if(name.length() < 2 || name.length() > 20){
			System.out.println("�ּ� 2�� ~ �ִ� 20�ڱ��� �Է� �����մϴ�.");
			return false;
		}

		for (char ch : name.toCharArray()) {
			if(!Character.isLetter(ch)){
				System.out.println("���ڸ� �Է� �����մϴ�.");
				return false;
			}
		}
		return true;
	}

	public boolean isValidUserPw(String userPw) {
		if (userPw.length() < 8 || userPw.length() > 20) {
			System.out.println("�ּ� 8�� ~ �ִ� 20�ڱ��� �Է� �����մϴ�.");
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
				// Ư�� ���� �� ������ ������ �������� ������� ����
				if (ch == ' ' || ch == '\'' || ch == '\"' || ch == '\\') {
					System.out.println("����, ���� ����ǥ, ū ����ǥ, �齽���ô� �Է��� �� �����ϴ�.");
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
			System.out.println("�빮��, �ҹ���, ����, Ư������ �� 2���� �̻� �ԷµǾ�� �մϴ�.");
			return false;
		}
	}

	public boolean isValidUserId(String userId) {
		if(userId.length() < 4 || userId.length() > 20){
			System.out.println("�ּ� 4�� ~ �ִ� 20�ڱ��� �Է� �����մϴ�.");
			return false;
		}

		if (Character.isDigit(userId.charAt(0))) {
			System.out.println("ù ���ڷ� ���ڸ� �Է��� �� �����ϴ�.");
			return false;
		}

		for (char ch : userId.toCharArray()) {
			if(!Character.isLetterOrDigit(ch)){
				System.out.println("���ڿ� ���ڸ� �Է� �����մϴ�.");
				return false;
			}
		}
		return true;
	}
}
