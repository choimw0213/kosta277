
import java.util.Date;

public class CommentVO {
	private int commentSeq;
	private int boardSeq;
	private String userId;
	private String commentContent;
	private Date commentDate;
	private int commentLike;

	public CommentVO(){}
	public CommentVO(
			int commentSeq, 
			int boardSeq, 
			String userId, 
			String commentContent, 
			Date commentDate,
			int commentLike) 
	{
		super();
		this.commentSeq = commentSeq;
		this.boardSeq = boardSeq;
		this.userId = userId;
		this.commentContent = commentContent;
		this.commentDate = commentDate;
		this.commentLike = commentLike;
	}
	
	public int getCommentSeq() {
		return commentSeq;
	}
	public void setCommentSeq(int commentSeq) {
		this.commentSeq = commentSeq;
	}
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public int getCommentLike() {
		return commentLike;
	}
	public void setCommentLike(int commentLike) {
		this.commentLike = commentLike;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boardSeq;
		result = prime * result + ((commentContent == null) ? 0 : commentContent.hashCode());
		result = prime * result + ((commentDate == null) ? 0 : commentDate.hashCode());
		result = prime * result + commentLike;
		result = prime * result + commentSeq;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		CommentVO other = (CommentVO) obj;
		if (boardSeq != other.boardSeq)
			return false;
		if (commentContent == null) {
			if (other.commentContent != null)
				return false;
		} else if (!commentContent.equals(other.commentContent))
			return false;
		if (commentDate == null) {
			if (other.commentDate != null)
				return false;
		} else if (!commentDate.equals(other.commentDate))
			return false;
		if (commentLike != other.commentLike)
			return false;
		if (commentSeq != other.commentSeq)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CommentVO [commentSeq=" + commentSeq + ", boardSeq=" + boardSeq + ", userId=" + userId
				+ ", commentContent=" + commentContent + ", commentDate=" + commentDate + ", commentLike=" + commentLike
				+ "]";
	}

}