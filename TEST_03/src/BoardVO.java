
import java.util.Date;

public class BoardVO {
	private int boardSeq;
	private String userId;
	private String category;
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private int boardView;
	private int boardLike;
	
	public BoardVO(){}
	public BoardVO(
			int boardSeq, 
			String userId, 
			String category, 
			String boardTitle, 
			String boardContent, 
			Date boardDate,
			int boardView, 
			int boardLike) 
	{
		super();
		this.boardSeq = boardSeq;
		this.userId = userId;
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardDate = boardDate;
		this.boardView = boardView;
		this.boardLike = boardLike;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public Date getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}
	public int getBoardView() {
		return boardView;
	}
	public void setBoardView(int boardView) {
		this.boardView = boardView;
	}
	public int getBoardLike() {
		return boardLike;
	}
	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardContent == null) ? 0 : boardContent.hashCode());
		result = prime * result + ((boardDate == null) ? 0 : boardDate.hashCode());
		result = prime * result + boardLike;
		result = prime * result + boardSeq;
		result = prime * result + ((boardTitle == null) ? 0 : boardTitle.hashCode());
		result = prime * result + boardView;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
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
		BoardVO other = (BoardVO) obj;
		if (boardContent == null) {
			if (other.boardContent != null)
				return false;
		} else if (!boardContent.equals(other.boardContent))
			return false;
		if (boardDate == null) {
			if (other.boardDate != null)
				return false;
		} else if (!boardDate.equals(other.boardDate))
			return false;
		if (boardLike != other.boardLike)
			return false;
		if (boardSeq != other.boardSeq)
			return false;
		if (boardTitle == null) {
			if (other.boardTitle != null)
				return false;
		} else if (!boardTitle.equals(other.boardTitle))
			return false;
		if (boardView != other.boardView)
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
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
		return "BoardVO [boardSeq=" + boardSeq + ", userId=" + userId + ", category=" + category + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardDate=" + boardDate + ", boardView="
				+ boardView + ", boardLike=" + boardLike + "]";
	}
}
