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
	/*	//�̰� �����������־�, �ֳ��ϸ� api �ݼ��� �þ�� �������ų� ���ѿ� ���� �����Ҽ� �����ϱ�. ����� �׳� �ѹ��� �޾Ƽ� �޼ҵ� �����Ἥ �̾Ƴ�. ���� �̷��ʿ���ݾ�.
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
	//	   // 1. Driver�� �ε��Ѵ�. 
	//	   Class.forName("oracle.jdbc.driver.OracleDriver"); 
	//	   System.out.println("OracleDriver�� �ε��� ���������� �̷������ϴ�."); 
	//	    
	//	   // 2. Connection ������ 
	//	   con = DriverManager.getConnection("jdbc:mysql://localhost",	"root", "autoset"); 
	//	   System.out.println("�����ͺ��̽��� ���ῡ �����Ͽ����ϴ�."); 
	//	    
	//	   // 3. Statement ��� --> ������ �ۼ��Ͽ� �����ϱ� ���� �뵵 
	//	   stmt = con.createStatement(); 
	//	    
	//	   // 4. ������ ���� -->> insert into (�ڵ����� commit �˴ϴ�.) 
	//	   String sql= "insert into test values(2,'ö��','10','��⵵')"; 
	//	   stmt.executeUpdate(sql); 
	//	   
	//	   // 5. ������Ʈ  -->> update �մϴ�.  (�ڵ����� commit �˴ϴ�.) 
	//	   sql = "update test set addr='������'" + "where name='����'"; 
	//	   stmt.executeUpdate(sql); 
	//	    
	//	   // 6. Delete ����  -->> delete �մϴ�. (�ڵ����� commit �˴ϴ�.) 
	//	   sql = "delete from test where name='�����屺'"; 
	//	   stmt.executeUpdate(sql); 
	//	     Class.forName("oracle.jdbc.driver.OracleDriver"); 
	//	   // 7. Select�� �����Ͽ� �����ͺ��̽� ���� ����ϱ� 
	//	   sql = "select * from test"; 
	//	   rs = stmt.executeQuery(sql); 
	//	   while(rs.next()){ 
	//	    System.out.println("�̸� : " + rs.getString("name") + "\t���� : " + rs.getString(3) + "\t�ּ� : " + rs.getString("addr")); 
	//	   } 
	//	   rs.close(); 
	//	   stmt.close(); 
	//	   con.close(); 
	//	  }catch(ClassNotFoundException cnfe){ 
	//	   System.out.println("oracle.jdbc.driver.OracleDriver�� ã�� �� �����ϴ�."); 
	//	  }catch(SQLException  sql){ 
	//	   System.out.println("Connection ����!"); 
	//	  }catch(Exception e){ 
	//	   System.out.println(e.toString()); 
	//	  }finally{ 
	//	   System.out.println("����!!"); 
	//	  } 