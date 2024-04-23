
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDAO {
	private final Connection connection;
	public BoardDAO() throws ClassNotFoundException, SQLException{
		connection = DBManager.getInstance().getConnection();
	}

	//홈화면 추천글 목록 불러오기(6개씩)
	public Map<String, List<BoardVO>> getBestBoard(){
		Map<String, List<BoardVO>> boardListMap = new HashMap<String, List<BoardVO>>();
		List<BoardVO> infoBoardList = new ArrayList<BoardVO>();
		List<BoardVO> freeBoardList = new ArrayList<BoardVO>();
		String sql = null;

		sql = "select * from ("
				+ "select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like "
				+ "from boards "
				+ "where category in ('공지', '이벤트', '기타') "
				+ "and board_like >= 10 "
				+ "order by board_date desc"
				+ ") where rownum <= 6";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				infoBoardList.add(new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql = "select * from ("
				+ "select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like "
				+ "from boards "
				+ "where category in ('잡담', '직업', '질문') "
				+ "and board_like >= 10 "
				+ "order by board_date desc"
				+ ") where rownum <= 6";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				freeBoardList.add(new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		boardListMap.put("info", infoBoardList);
		boardListMap.put("free", freeBoardList);

		return boardListMap;
	}
	//게시글 조회(분류, 검색, 정렬, 페이징)
	public List<BoardVO> getBoard(ViewDTO dto){
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		StringBuilder query = new StringBuilder("select * from (select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like from boards where 1=1 ");

		// 분류기능
		if(dto.getCategory().equals("정보전체")){
			query.append(" and category in ('공지', '이벤트', '기타') ");
		} else if(dto.getCategory().equals("자유전체")){
			query.append(" and category in ('잡담', '직업', '질문') ");
		} else if(dto.getCategory().equals("공지")){
			query.append(" and category in ('공지') ");
		} else if(dto.getCategory().equals("이벤트")){
			query.append(" and category in ('이벤트') ");
		} else if(dto.getCategory().equals("기타")){
			query.append(" and category in ('기타') ");
		} else if(dto.getCategory().equals("잡담")){
			query.append(" and category in ('잡담') ");
		} else if(dto.getCategory().equals("직업")){
			query.append(" and category in ('직업') ");
		} else if(dto.getCategory().equals("질문")){
			query.append(" and category in ('질문') ");
		} else if(dto.getCategory().equals("정보 추천10이상")){
			query.append(" and category in ('공지', '이벤트', '기타') and board_like >= 10 ");
		} else if(dto.getCategory().equals("자유 추천10이상")){
			query.append(" and category in ('잡담', '직업', '질문') and board_like >= 10 ");
		}

		// 검색기능
		if(dto.getSearchType().equals("제목")){
			query.append(" and board_title like '%" + dto.getSearchWord() + "%' ");
		} else if(dto.getSearchType().equals("내용")){
			query.append(" and board_content like '%" + dto.getSearchWord() + "%' ");			
		} else if(dto.getSearchType().equals("제목내용")){
			query.append(" and board_title like '%" + dto.getSearchWord() + "%' or board_content like '%" + dto.getSearchWord() + "%' ");
		} else if(dto.getSearchType().equals("작성자")){
			query.append(" and user_id like '%" + dto.getSearchWord() + "%' ");
		}

		// 정렬기능
		if(dto.getSortType().equals("작성일 오름차순")){
			query.append(" order by board_date asc) ");
		} else if(dto.getSortType().equals("작성일 내림차순")){
			query.append(" order by board_date desc) ");
		} else if(dto.getSortType().equals("조회수 오름차순")){
			query.append(" order by board_view asc) ");
		} else if(dto.getSortType().equals("조회수 내림차순")){
			query.append(" order by board_view desc) ");
		} else if(dto.getSortType().equals("추천수 오름차순")){
			query.append(" order by board_like asc) ");
		} else if(dto.getSortType().equals("추천수 내림차순")){
			query.append(" order by board_like desc) ");
		}

		// 페이징기능
		int i = dto.getPageNumber();
		String start = Integer.toString(10*(i-1));
		String end = Integer.toString(10*i);
		query.append("where rownum > " + start + " and rownum <= " + end);
		System.out.println(query.toString());

		// SQL
		try {
			PreparedStatement pstmt = connection.prepareStatement(query.toString());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				boardList.add(new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return boardList;
	}
	//마이페이지(내 게시글)
	public List<BoardVO> getBoard(String userId, String sortType, int pageNumber){
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		StringBuilder query = new StringBuilder("select * from (select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like from boards where 1=1 ");

		// 검색기능
		query.append(" and user_id = ? ");

		// 정렬기능
		if(sortType.equals("작성일 오름차순")){
			query.append(" order by board_date asc) ");
		} else if(sortType.equals("작성일 내림차순")){
			query.append(" order by board_date desc) ");
		} else if(sortType.equals("조회수 오름차순")){
			query.append(" order by board_view asc) ");
		} else if(sortType.equals("조회수 내림차순")){
			query.append(" order by board_view desc) ");
		} else if(sortType.equals("추천수 오름차순")){
			query.append(" order by board_like asc) ");
		} else if(sortType.equals("추천수 내림차순")){
			query.append(" order by board_like desc) ");
		}

		// 페이징기능
		int i = pageNumber;
		String start = Integer.toString(10*(i-1));
		String end = Integer.toString(10*i);
		query.append("where rownum > " + start + " and rownum <= " + end);
		System.out.println(query.toString());
		
		// SQL
		try {
			PreparedStatement pstmt = connection.prepareStatement(query.toString());
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				boardList.add(new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boardList;
	}
	//게시글 등록
	public boolean createBoard(BoardVO vo){
		String sql = null;

		sql = "insert into boards values (board_seq.nextval, ?, ?, ?, ?, sysdate, 0, 0)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getUserId());
			pstmt.setString(2, vo.getCategory());
			pstmt.setString(3, vo.getBoardTitle());
			pstmt.setString(4, vo.getBoardContent());
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//홈화면 추천글 읽기
	public BoardVO readBoard(int boardSeq){
		BoardVO vo = null;
		String sql = null;

		sql = "select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like "
				+ "from boards "
				+ "where board_seq = ?";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				sql = "update boards set board_view = ? where board_seq = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, (rs.getInt(7)+1));
				pstmt.setInt(2, boardSeq);
				if(pstmt.executeUpdate() == 1){
					vo = new BoardVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)+1, rs.getInt(8));	
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	//게시글 수정
	public boolean updateBoard(BoardVO vo){
		String sql = null;

		sql = "update boards set category = ?, board_title = ?, board_content = ? where board_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, vo.getCategory());
			pstmt.setString(2, vo.getBoardTitle());
			pstmt.setString(3, vo.getBoardContent());
			pstmt.setInt(4, vo.getBoardSeq());
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//게시글 삭제
	public boolean deleteBoard(int boardSeq){
		String sql1 = "delete from comment_likes where comment_seq in (select comment_seq from comments where board_seq = ?)";
		String sql2 = "delete from comments where board_seq = ?";
		String sql3 = "delete from board_likes where board_seq = ?";
		String sql4 = "delete from boards where board_seq = ?";

		try {
			PreparedStatement pstmt = null;

			pstmt = connection.prepareStatement(sql1);
			pstmt.setInt(1, boardSeq);
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql2);
			pstmt.setInt(1, boardSeq);
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql3);
			pstmt.setInt(1, boardSeq);
			pstmt.executeUpdate();

			pstmt = connection.prepareStatement(sql4);
			pstmt.setInt(1, boardSeq);
			if(pstmt.executeUpdate() == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//게시글 추천기능(증가, 감소, 개수)
	public boolean createBoardLike(String userId, int boardSeq){
		String sql = null;

		sql = "insert into board_likes values(?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, boardSeq);
			if(pstmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return synchronizeBoardLike(boardSeq);
	}
	public boolean deleteBoardLike(String userId, int boardSeq){
		String sql = null;

		sql = "delete from board_likes where user_id = ? and board_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, boardSeq);
			if(pstmt.executeUpdate() != 1) return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return synchronizeBoardLike(boardSeq);
	}
	public boolean synchronizeBoardLike(int boardSeq){
		String sql = null;

		sql = "select count(board_seq) from board_likes where board_seq = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				sql = "update boards set board_like = ? where board_seq = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, rs.getInt(1));
				pstmt.setInt(2, boardSeq);
				if(pstmt.executeUpdate() == 1) return true;
			} else {
				return false;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public int getBoardLike(int boardSeq){
		String sql = null;

		sql = "select board_like from boards where board_seq = ?";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, boardSeq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
