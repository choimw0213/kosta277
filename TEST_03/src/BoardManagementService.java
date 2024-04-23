import java.sql.SQLException;
import java.util.Date;

public class BoardManagementService {
	private final UserDAO userDAO;
	private final BoardDAO boardDAO;
	private final CommentDAO commentDAO;
	
	public BoardManagementService(){
		try {
			userDAO = new UserDAO();
			boardDAO = new BoardDAO();
			commentDAO = new CommentDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
            System.out.println("�����ͺ��̽� ���ῡ �����߽��ϴ�.");
            throw new RuntimeException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
            System.out.println("�����ͺ��̽� ����̹� �ε��� �����߽��ϴ�.");
            throw new RuntimeException(e);
		}
	}
	
	public void service(){
//		UserDAO �׽�Ʈ
//		System.out.println(userDAO.createUser("user103", "password103", "name", "01012340003", "nickname103"));
//		System.out.println(userDAO.readUser("user102"));
//		System.out.println(userDAO.updateUser("user102", "password102", "name", "01012340002", "nickname102"));
//		System.out.println(userDAO.deleteUser(userDAO.readUser("user100")));
		System.out.println(userDAO.logIn("user99", "password999"));
//		System.out.println(userDAO.getUserId("name99", "01016887369"));
//		System.out.println(userDAO.getUserPw("user99", "name99", "01016887369"));
	
//		BoardDAO �׽�Ʈ
//		System.out.println(boardDAO.getBestBoard());
//		System.out.println(boardDAO.getBoard(new ViewDTO("������ü","�ۼ���","user1","�ۼ��� ��������",1)));	
//		System.out.println(boardDAO.getBoard("user1", "�ۼ��� ��������", 1));
//		System.out.println(boardDAO.createBoard(new BoardVO(1,"user1","��Ÿ","����1","����1",new Date(System.currentTimeMillis()),0,0)));
//		System.out.println(boardDAO.readBoard(1012));
//		System.out.println(boardDAO.updateBoard(new BoardVO(1,"user1","��Ÿ","����1","����1",new Date(System.currentTimeMillis()),0,0)));
//		System.out.println(boardDAO.deleteBoard(1013));
//		System.out.println(boardDAO.createBoardLike("user5", 1012));
//		System.out.println(boardDAO.deleteBoardLike("user3", 1012));
//		System.out.println(boardDAO.getBoardLike(1012));
		
//		CommentDAO �׽�Ʈ
//		System.out.println(commentDAO.getBestComment(500));
//		System.out.println(commentDAO.getComment("user30"));
//		System.out.println(commentDAO.createComment(new CommentVO(1,100,"user1","��۳���1111",new Date(System.currentTimeMillis()),0)));
//		System.out.println(commentDAO.readComment(100));
//		System.out.println(commentDAO.updateComment(new CommentVO(1,100,"user1","��۳���1111",new Date(System.currentTimeMillis()),0)));
//		System.out.println(commentDAO.deleteComment(2567));
//		System.out.println(commentDAO.createCommentLike("user2", 2566));
//		System.out.println(commentDAO.deleteCommentLike("user1", 2566));
//		System.out.println(commentDAO.getCommentLike(2566));
	}
}
