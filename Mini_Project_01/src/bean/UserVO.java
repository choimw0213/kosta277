package bean;

import java.util.Date;

public class UserVO {
	private int userSeq;
	private String userId;
	private String userPw;
	private String userName;
	private String userGender;
	private String userPhone;
	private Date userDate;

	public UserVO(){}
	public UserVO(int userSeq, 
			String userId, 
			String userPw, 
			String userName, 
			String userGender, 
			String userPhone,
			Date userDate) 
	{
		super();
		setUserSeq(userSeq);
		setUserId(userId);
		setUserPw(userPw);
		setUserName(userName);
		setUserGender(userGender);
		setUserPhone(userPhone);
		setUserDate(userDate);
	}
	
	public int getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Date getUserDate() {
		return userDate;
	}
	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userDate == null) ? 0 : userDate.hashCode());
		result = prime * result + ((userGender == null) ? 0 : userGender.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPhone == null) ? 0 : userPhone.hashCode());
		result = prime * result + ((userPw == null) ? 0 : userPw.hashCode());
		result = prime * result + userSeq;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVO other = (UserVO) obj;
		if (userDate == null) {
			if (other.userDate != null)
				return false;
		} else if (!userDate.equals(other.userDate))
			return false;
		if (userGender == null) {
			if (other.userGender != null)
				return false;
		} else if (!userGender.equals(other.userGender))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPhone == null) {
			if (other.userPhone != null)
				return false;
		} else if (!userPhone.equals(other.userPhone))
			return false;
		if (userPw == null) {
			if (other.userPw != null)
				return false;
		} else if (!userPw.equals(other.userPw))
			return false;
		if (userSeq != other.userSeq)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserVO [userSeq=" + userSeq + ", userId=" + userId + ", userPw=" + userPw + ", userName=" + userName
				+ ", userGender=" + userGender + ", userPhone=" + userPhone + ", userDate=" + userDate + "]";
	}

}
