//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class dbTest {
//	
//	private static Connection con; 
//	private static Statement stmt; 
//	private static ResultSet rs;
//	private static String ss;
//	
//	
//	
//	public static void main(String[] args){
//		try {
//			Connection con = null;
//
//			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);
//
//			java.sql.Statement st = null;
//			ResultSet rs = null;
//			st = con.createStatement();
//			rs = st.executeQuery("select * from updates");
//
//			if (st.execute("select * from updates")) {
//				rs = st.getResultSet();
//			}
//
//			while (rs.next()) {
//				String str = rs.getString(1);
//				System.out.println(str);
//			}
//		} catch (SQLException sqex) {
//			System.out.println("SQLException: " + sqex.getMessage());
//			System.out.println("SQLState: " + sqex.getSQLState());
//		}
//
//
//
//	}
//}
//	private static List<FBComment> getCommentFromPost(FacebookClient client, String post_id){
	//	    List<String> comments = new ArrayList<FBComment>();
	//
	//	    Connection<Comment> allComments = client.fetchConnection(post_id+"/comments", Comment.class);
	//	    for(List<Comment> postcomments : allComments){
	//	        for (Comment comment : postcomments){
	//	        long likes     = comment.getLikeCount()==null?(comment.getLikes()==null?0:comment.getLikes()):comment.getLikeCount();
	//	        comments.add(comment.getMessage()+" - "+likes);
	//	        }
	//	    }
	//
	//
	//	    return comments;
	//	}
	/*	//이게 안좋을수도있어, 왜냐하면 api 콜수가 늘어나면 느려지거나 제한에 빨리 도달할수 있으니깐. 대안은 그냥 한번에 받아서 메소드 직접써서 뽑아내. 굳이 이럴필요없잖아.
	public String getMessage(String id){
		Post post = fbClient.fetchObject(id,
				  Post.class,
				  Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
		return post.getMessage();
	}
	public Long getLikes(String id){
		Post post = fbClient.fetchObject(id,
				  Post.class,
				  Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
		return post.getLikesCount();
	}
	public Long getComments(String id){
		Post post = fbClient.fetchObject(id,
				  Post.class,
				  Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
		return post.getCommentsCount(); 
	}
	public Long getShares(String id){
		Post post = fbClient.fetchObject(id,
				  Post.class,
				  Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
		return post.getSharesCount();
	}*/

//	public void initDB() {
//		try {
//			Connection con = null;
//
//			con = DriverManager.getConnection(url,
//					id, pw);
//
//			java.sql.Statement st = null;
//			ResultSet rs = null;
//			st = con.createStatement();
//			rs = st.executeQuery("SHOW DATABASES");
//
//			if (st.execute("SHOW DATABASES")) {
//				rs = st.getResultSet();
//			}
//
//			while (rs.next()) {
//				String str = rs.getNString(1);
//				System.out.println(str);
//			}
//		} catch (SQLException sqex) {
//			System.out.println("SQLException: " + sqex.getMessage());
//			System.out.println("SQLState: " + sqex.getSQLState());
//		}
//
//	}


	//	 db.

	//	  try{ 
	//	   // 1. Driver를 로딩한다. 
	//	   Class.forName("oracle.jdbc.driver.OracleDriver"); 
	//	   System.out.println("OracleDriver의 로딩이 정상적으로 이뤄졌습니다."); 
	//	    
	//	   // 2. Connection 얻어오기 
	//	   con = DriverManager.getConnection("jdbc:mysql://localhost",	"root", "autoset"); 
	//	   System.out.println("데이터베이스의 연결에 성공하였습니다."); 
	//	    
	//	   // 3. Statement 얻기 --> 쿼리문 작성하여 적용하기 위한 용도 
	//	   stmt = con.createStatement(); 
	//	    
	//	   // 4. 쿼리문 실행 -->> insert into (자동으로 commit 됩니다.) 
	//	   String sql= "insert into test values(2,'철이','10','경기도')"; 
	//	   stmt.executeUpdate(sql); 
	//	   
	//	   // 5. 업데이트  -->> update 합니다.  (자동으로 commit 됩니다.) 
	//	   sql = "update test set addr='별나라'" + "where name='순이'"; 
	//	   stmt.executeUpdate(sql); 
	//	    
	//	   // 6. Delete 삭제  -->> delete 합니다. (자동으로 commit 됩니다.) 
	//	   sql = "delete from test where name='똘이장군'"; 
	//	   stmt.executeUpdate(sql); 
	//	     Class.forName("oracle.jdbc.driver.OracleDriver"); 
	//	   // 7. Select문 실행하여 데이터베이스 내용 출력하기 
	//	   sql = "select * from test"; 
	//	   rs = stmt.executeQuery(sql); 
	//	   while(rs.next()){ 
	//	    System.out.println("이름 : " + rs.getString("name") + "\t나이 : " + rs.getString(3) + "\t주소 : " + rs.getString("addr")); 
	//	   } 
	//	   rs.close(); 
	//	   stmt.close(); 
	//	   con.close(); 
	//	  }catch(ClassNotFoundException cnfe){ 
	//	   System.out.println("oracle.jdbc.driver.OracleDriver를 찾을 수 없습니다."); 
	//	  }catch(SQLException  sql){ 
	//	   System.out.println("Connection 실패!"); 
	//	  }catch(Exception e){ 
	//	   System.out.println(e.toString()); 
	//	  }finally{ 
	//	   System.out.println("성공!!"); 
	//	  } 