
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;



public class Jdbc {
	private String url;
	private String id;
	private String pw;
	private Connection con;
	
	
	/**
	 * DB���ٿ� �ʿ��� ������ �ʱ�ȭ�ϴ� �������Դϴ�.
	 */
	public Jdbc(String url, String id, String pw){
		this.url = url;
		this.id = id;
		this.pw = pw;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	public void closeConnection(){

		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �ֱ� ������Ʈ �Ͻø� DB�κ��� �ҷ��ɴϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.  <p>��� �б��� ������Ʈ �Ͻø� �޾ƿ��� �����մϴ�.  
	 * @return 
	 * 		�ֱ� ������Ʈ �Ͻø� String���·� ��ȯ�մϴ�.<p> 
	 */
	public String getRecentUpdate(int univKey){
		String recentUpdate = null;
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("SELECT time FROM recentUpdates WHERE univKey="+univKey+";");
			if(rs.next()){ 
				recentUpdate = rs.getString(1);				
			}
			else{
				recentUpdate = Constants.defaultRecentUpdate;					
			}
		}catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
			
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		}
		return recentUpdate;
	}
	
	
	
	/**
	 *  �ֱ� ������Ʈ �Ͻø� ���ڷ� �޾� DB�� �����մϴ�.
	 * @param dateString
	 * 		������ �Ͻ�. ����/������ ���Ǹ� ���� 'yyyy-MM-dd HH:mm:ss'������ String���� �޾ƿɴϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.
	 */
	public void setRecentUpdate(String dateString, int univKey){
		if (getRecentUpdate(univKey).equals(Constants.defaultRecentUpdate))
			updateQuery("INSERT INTO recentUpdates(time,univKey) values('" + dateString + "',"+univKey+");");
		else
			updateQuery("UPDATE recentUpdates SET time='"+dateString+"', univKey="+univKey+" WHERE univKey="+univKey+";");
	}
	/**
	 *  �ֱ� ������Ʈ �Ͻø� ����ð������Ͽ� DB�� �����մϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.
	 */
	public void setRecentUpdate(int univKey){
		if (getRecentUpdate(univKey).equals(Constants.defaultRecentUpdate))
			updateQuery("INSERT INTO recentUpdates(time,univKey) values(NOW(),"+univKey+");");
		else
			updateQuery("UPDATE recentUpdates SET time=NOW(), univKey="+univKey+" WHERE univKey="+univKey+";");
	}
	
	public void setUpdate(String work){
		updateQuery("INSERT INTO updates(time,work) values(NOW(),'"+work+"');");		
	}
	
	/**
	 * �� �۵��� ������ ������ DB�� �����մϴ�.<p>
	 * ���⿡���� id�� �ۼ� �Ͻ�, �б��� �����մϴ�.
	 * @param createdTime
	 * 		�ۼ� �Ͻ�.
	 */
	public void writeSimpleInformations(ArrayList<Article> articles){
		for(Article article : articles){
			updateQuery(
					"INSERT INTO articles(id,created_time,univKey) "+
					"VALUES('" + article.getId() + "','" +
								 article.getCreatedTime() + "', " +
								 article.getUnivKey() + ");"
						);	
		}
		
	}
	
	
	
	
	/**
	 * �б��̸��� �ش��ϴ� ��� ���� id���� DB�� �ش� ���̺�κ��� �ҷ��ɴϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.
	 * @param tableName
	 * 		DB���� ���̺� ��.
	 */
	public ArrayList<String> readIds(int univKey,String tableName){
		ArrayList<String> list = new ArrayList<String>();
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
//			String ���ݺ���6������ = Constants.fm.format(new Date()) new Date());
			rs = st.executeQuery("SELECT id FROM "+tableName+
					" WHERE univKey=" + univKey + ";"); 
//					" and unix_timestamp(created_time) > unix_timestamp('"+���ݺ���6������+"') ;"); 
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		}
		return list;
	}
	
	
	/**
	 * �б��̸��� �ش��ϴ� ��� ���� DB�� �ش� ���̺�κ��� �ҷ��ɴϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.
	 * @param tableName
	 * 		DB���� ���̺� ��.
	 * @return
	 * 		ArrayList<Article>���·� ��ȯ�մϴ�.
	 */
	public ArrayList<Article> readArticles(int univKey,String tableName){
		ArrayList<Article> articles = new ArrayList<Article>();
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM "+tableName+" WHERE univKey=" + univKey + ";");
			while (rs.next()) {
				Article article = new Article(
						rs.getString("id"),
						rs.getString("created_time"),
						univKey,
						rs.getInt("interesting"),
						rs.getInt("likes"),
						rs.getInt("comments"),
						rs.getInt("shares")
						);
				articles.add(article);
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	
	
	/**
	 * ���ڷ� ���� �Խñ� ����Ʈ ���� ��� �Խñ��� ���ƿ�,���,���� Ƚ���� DB�� ������Ʈ�մϴ�.<p>
	 * @param articles
	 * 		�Խñ��� ArrayList�� �޾ƿɴϴ�.
	 */
	public void writeInterestInformations(ArrayList<Article> articles){
		for(Article article : articles){
			int likes = article.getLikes();
			int comments = article.getComments();
			int shares = article.getShares();
			int interesting = likes*3+comments*2+shares*5;
			updateQuery("UPDATE articles SET " +
				    "likes="+likes+", "+
				    "comments="+comments+", "+
				    "shares="+shares+", "+
				    "interesting="+interesting+
				    " WHERE id='"+article.getId()+"';");
			
			updateQuery("UPDATE interestingArticles SET " +
				    "likes="+likes+", "+
				    "comments="+comments+", "+
				    "shares="+shares+", "+
				    "interesting="+interesting+
				    " WHERE id='"+article.getId()+"';");	
		}
		
	}
	
	
	
	public void updateWeekTable(){
		
	}
	
	public void updateBestTable(){
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 *  �б��� ��̵��� ������ ���� ��� �۵��� DB�κ��� �޾ƿ�
	 *  Article�� ArrayList ���·� ��ȯ�մϴ�. 
	 *  <p> DB���� 'articles'���̺��� ������ ��ȯ�մϴ�.
	 *  <p> �ֱ� 30�ϵ��� �������س��� ���� n��.
	 * @param univKey
	 * 		��� �б���ȣ.
	 * @param limitNumber
	 * 		�� �� ����.
	 * @return
	 * 		Article�� ArrayList���·�  ��ȯ.
	 */
	public ArrayList<Article> fetchInterestingArticles(int univKey, int limitNumber){
		ArrayList<Article> articles = new ArrayList<Article>();
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			String query = 
					  "SELECT * "
					+ "FROM articles "
					+ "WHERE univKey = " + univKey
					+ " and interesting > "+ Constants.interestingMin
					+ " and date(created_time) >= date(subdate(NOW(), INTERVAL 30 DAY))"
					+ " ORDER BY interesting DESC"
					+ " LIMIT "+limitNumber
					+ ";";
							
			rs = st.executeQuery(query);
			while (rs.next()) {
				Article article = new Article(
						rs.getString("id"),
						rs.getString("created_time"),
						univKey,
						rs.getInt("interesting"),
						rs.getInt("likes"),
						rs.getInt("comments"),
						rs.getInt("shares")
						);
				articles.add(article);
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		}
		return articles;
	}
	
	/**
	 *  �б��� ��̵��� ������ ���� ��� �۵��� DB�κ��� �޾ƿ�
	 *  Article�� ArrayList ���·� ��ȯ�մϴ�. 
	 *  <p> ���������� �ƴϰ� �������� ������ ������.
	 *  <p> DB���� 'articles'���̺��� ������ ��ȯ�մϴ�.
	 * @param univKey
	 * 		��� �б���ȣ.
	 * 
	 * @return
	 * 		Article�� ArrayList���·�  ��ȯ.
	 */
	public ArrayList<Article> fetchInterestingArticles(int univKey){
		ArrayList<Article> articles = new ArrayList<Article>();
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			String query = 
					  "SELECT * "
					+ "FROM articles "
					+ "WHERE univKey = " + univKey
					+ " and interesting > "+ Constants.interestingMin
//					+ " and date(created_time) >= date(subdate(NOW(), INTERVAL 30 DAY))"
					+ " and id not in (select id from interestingArticles)"
//					+ " ORDER BY interesting DESC"
//					+ " LIMIT "+50
					+ ";";
							
			rs = st.executeQuery(query);
			while (rs.next()) {
				Article article = new Article(
						rs.getString("id"),
						rs.getString("created_time"),
						univKey,
						rs.getInt("interesting"),
						rs.getInt("likes"),
						rs.getInt("comments"),
						rs.getInt("shares")
						);
				articles.add(article);
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		}
		return articles;
	}
	
	
	
	
	/**
	 * ��̷ο� �۵��� DB�� �����մϴ�. 
	 * <p> interestingArticles ���̺� �����մϴ�. 
	 * @param articles
	 * 		������ ���� Article�� ArrayList(articles)���·� �����մϴ�.
	 */
			
	public void writeInterestingArticles(ArrayList<Article> articles){
		
		for (Article article : articles){
			String message = article.getMessage();
			if (message!=null){
				message = message.replaceAll("\'", "��");//��
				message = message.replaceAll("\"", "\\����");
				
			}else
				message = "������ ���� ���Դϴ�.";
			updateQuery(
					"INSERT INTO interestingArticles(id,likes,comments,shares,message,created_time,interesting,univKey) "+
					"VALUES('" + article.getId() +"', "
							+ article.getLikes() +", "
							+ article.getComments() +", "
							+ article.getShares() +", '"
							+ message +"', '"
							+ article.getCreatedTime() +"', "
							+ article.getInteresting() +", "
							+ article.getUnivKey() +");"
						);
			
		}
	}
	
	
	/**
	 * DB�� select��û�� ������, update,delete,insert��û�� SQL���� �����մϴ�. 
	 * @param q
	 * 		String ������ SQL���Դϴ�.	  
	 * 		
	 */
	private void updateQuery(String q){
		try{ 
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement stmt = null;
			stmt = con.createStatement();
			stmt.executeUpdate(q);		
			stmt.close();
			con.close();
		}catch(SQLException  sqle){ 
			System.err.println(sqle.toString());
		}catch(Exception e){ 
			System.out.println(e.toString()); 
		}
	}
}


// write�� DB�� ������� �ܶ� ���°Ŷ� write.
// read�� DB���� ������� �ܶ� �д°Ŷ� read.
// DB���������� get,set�� (���к�) ���� �� �����̶� get,set��������.  
