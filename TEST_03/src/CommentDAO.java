
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
	private final Connection connection;
	public CommentDAO() throws ClassNotFoundException, SQLException{
		connection = DBManager.getInstance().getConnection();
	}

	//추천댓글
	public CommentVO getBestComment(int boardSeq){
		CommentVO vo = null;
		String sql = null;

		sql = "select * "
				+ "from(select comment_seq, board_seq, user_id, comment_content, comment_date, comment_like "
				+ "from comments "
				+ "where board_seq = ? "
				+ "order by comment_like desc, comment_date desc) "
				+ "where rownum = 1";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				vo = new CommentVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	//마이페이지(내 댓글)
	public List<CommentVO> getComment(String userId){
		List<CommentVO> commentList = new ArrayList<CommentVO>();
		String sql = null;

		sql = "select comment_seq, board_seq, user_id, comment_content, comment_date, comment_like "
				+ "from comments "
				+ "where user_id = ? "
				+ "order by comment_date desc";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				commentList.add(new CommentVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentList;
	}
	//댓글 등록
	public boolean createComment(CommentVO vo){
		String sql = null;

		sql = "insert into comments values (comment_seq.nextval, ?, ?, ?, sysdate, 0)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, vo.getBoardSeq());
			pstmt.setString(2, vo.getUserId());
			pstmt.setString(3, vo.getCommentContent());
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//댓글 조회
	public List<CommentVO> readComment(int boardSeq){
		List<CommentVO> commentList = new ArrayList<CommentVO>();
		String sql = null;

		sql = "select comment_seq, board_seq, user_id, comment_content, comment_date, comment_like "
				+ "from comments "
				+ "where board_seq = ? "
				+ "order by comment_date asc";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardSeq);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				commentList.add(new CommentVO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getInt(6)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentList;
	}
	//댓글 수정
	public boolean updateComment(CommentVO vo){
		String sql = null;

		sql = "update comments set comment_content = ? where comment_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getCommentContent());
			pstmt.setInt(2, vo.getCommentSeq());
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//댓글 삭제
	public boolean deleteComment(int commentSeq){
		String sql1 = "delete from comment_likes where comment_seq = ?";
		String sql2 = "delete from comments where comment_seq = ?";

		try {
			PreparedStatement pstmt = null;

			pstmt = connection.prepareStatement(sql1);
			pstmt.setInt(1, commentSeq);
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, commentSeq);
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//댓글 추천기능(등록, 취소, 개수)
	public boolean createCommentLike(String userId, int commentSeq){
		String sql = null;

		sql = "insert into comment_likes values(?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, commentSeq);
			if(pstmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return synchronizeCommentLike(commentSeq);
	}
	public boolean deleteCommentLike(String userId, int commentSeq){
		String sql = null;

		sql = "delete from comment_likes where user_id = ? and comment_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, commentSeq);
			if(pstmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return synchronizeCommentLike(commentSeq);
	}
	public boolean synchronizeCommentLike(int commentSeq) {
		// TODO Auto-generated method stub
		int commentLike = 0;
		String sql = null;

		sql = "select count(comment_seq) from comment_likes where comment_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, commentSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				commentLike = rs.getInt(1);
			} else {
				return false;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql = "update comments set comment_like = ? where comment_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, commentLike);
			pstmt.setInt(2, commentSeq);
			if(pstmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public int getCommentLike(int commentSeq){
		String sql = null;

		sql = "select comment_like from comments where comment_seq = ?";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, commentSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
