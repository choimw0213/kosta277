
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

	//Ȩȭ�� ��õ�� ��� �ҷ�����(6����)
	public Map<String, List<BoardVO>> getBestBoard(){
		Map<String, List<BoardVO>> boardListMap = new HashMap<String, List<BoardVO>>();
		List<BoardVO> infoBoardList = new ArrayList<BoardVO>();
		List<BoardVO> freeBoardList = new ArrayList<BoardVO>();
		String sql = null;

		sql = "select * from ("
				+ "select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like "
				+ "from boards "
				+ "where category in ('����', '�̺�Ʈ', '��Ÿ') "
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
				+ "where category in ('���', '����', '����') "
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
	//�Խñ� ��ȸ(�з�, �˻�, ����, ����¡)
	public List<BoardVO> getBoard(ViewDTO dto){
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		StringBuilder query = new StringBuilder("select * from (select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like from boards where 1=1 ");

		// �з����
		if(dto.getCategory().equals("������ü")){
			query.append(" and category in ('����', '�̺�Ʈ', '��Ÿ') ");
		} else if(dto.getCategory().equals("������ü")){
			query.append(" and category in ('���', '����', '����') ");
		} else if(dto.getCategory().equals("����")){
			query.append(" and category in ('����') ");
		} else if(dto.getCategory().equals("�̺�Ʈ")){
			query.append(" and category in ('�̺�Ʈ') ");
		} else if(dto.getCategory().equals("��Ÿ")){
			query.append(" and category in ('��Ÿ') ");
		} else if(dto.getCategory().equals("���")){
			query.append(" and category in ('���') ");
		} else if(dto.getCategory().equals("����")){
			query.append(" and category in ('����') ");
		} else if(dto.getCategory().equals("����")){
			query.append(" and category in ('����') ");
		} else if(dto.getCategory().equals("���� ��õ10�̻�")){
			query.append(" and category in ('����', '�̺�Ʈ', '��Ÿ') and board_like >= 10 ");
		} else if(dto.getCategory().equals("���� ��õ10�̻�")){
			query.append(" and category in ('���', '����', '����') and board_like >= 10 ");
		}

		// �˻����
		if(dto.getSearchType().equals("����")){
			query.append(" and board_title like '%" + dto.getSearchWord() + "%' ");
		} else if(dto.getSearchType().equals("����")){
			query.append(" and board_content like '%" + dto.getSearchWord() + "%' ");			
		} else if(dto.getSearchType().equals("���񳻿�")){
			query.append(" and board_title like '%" + dto.getSearchWord() + "%' or board_content like '%" + dto.getSearchWord() + "%' ");
		} else if(dto.getSearchType().equals("�ۼ���")){
			query.append(" and user_id like '%" + dto.getSearchWord() + "%' ");
		}

		// ���ı��
		if(dto.getSortType().equals("�ۼ��� ��������")){
			query.append(" order by board_date asc) ");
		} else if(dto.getSortType().equals("�ۼ��� ��������")){
			query.append(" order by board_date desc) ");
		} else if(dto.getSortType().equals("��ȸ�� ��������")){
			query.append(" order by board_view asc) ");
		} else if(dto.getSortType().equals("��ȸ�� ��������")){
			query.append(" order by board_view desc) ");
		} else if(dto.getSortType().equals("��õ�� ��������")){
			query.append(" order by board_like asc) ");
		} else if(dto.getSortType().equals("��õ�� ��������")){
			query.append(" order by board_like desc) ");
		}

		// ����¡���
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
	//����������(�� �Խñ�)
	public List<BoardVO> getBoard(String userId, String sortType, int pageNumber){
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		StringBuilder query = new StringBuilder("select * from (select board_seq, user_id, category, board_title, board_content, board_date, board_view, board_like from boards where 1=1 ");

		// �˻����
		query.append(" and user_id = ? ");

		// ���ı��
		if(sortType.equals("�ۼ��� ��������")){
			query.append(" order by board_date asc) ");
		} else if(sortType.equals("�ۼ��� ��������")){
			query.append(" order by board_date desc) ");
		} else if(sortType.equals("��ȸ�� ��������")){
			query.append(" order by board_view asc) ");
		} else if(sortType.equals("��ȸ�� ��������")){
			query.append(" order by board_view desc) ");
		} else if(sortType.equals("��õ�� ��������")){
			query.append(" order by board_like asc) ");
		} else if(sortType.equals("��õ�� ��������")){
			query.append(" order by board_like desc) ");
		}

		// ����¡���
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
	//�Խñ� ���
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
	//Ȩȭ�� ��õ�� �б�
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
	//�Խñ� ����
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
	//�Խñ� ����
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
	//�Խñ� ��õ���(����, ����, ����)
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
