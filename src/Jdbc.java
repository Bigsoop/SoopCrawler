
import java.sql.*;
import java.util.ArrayList;



public class Jdbc {
	private String url;
	private String id;
	private String pw;
	private static Connection con=null;
	
	
	/**
	 * DB���ٿ� �ʿ��� ������ �ʱ�ȭ�ϴ� �������Դϴ�.
	 */
	public Jdbc(String url, String id, String pw){
		this.url = url;
		this.id = id;
		this.pw = pw;
	}

	
	
	/**
	 * �ֱ� ������Ʈ �Ͻø� DB�κ��� �ҷ��ɴϴ�.
	 * @param univName
	 * 		��� �б���.  <p>��� �б��� ������Ʈ �Ͻø� �޾ƿ��� �����մϴ�.  
	 * @return 
	 * 		�ֱ� ������Ʈ �Ͻø� String���·� ��ȯ�մϴ�.<p> 
	 */
	public String getRecentUpdate(String univName){
		String recentUpdate = null;
		try {			
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw); 
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("SELECT MAX(time) FROM updates WHERE univName='"+univName+"';");
			if (st.execute("SELECT MAX(time) FROM updates WHERE univName='"+univName+"';")) {
				rs = st.getResultSet();
			}
			while (rs.next()) {
				recentUpdate = rs.getString(1);
			}
			if (recentUpdate == null){
				recentUpdate = Constants.defaultRecentUpdate;
				
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
			
		}
		return recentUpdate;
	}
	
	
	
	/**
	 *  �ֱ� ������Ʈ �Ͻø� ���ڷ� �޾� DB�� �����մϴ�.
	 * @param dateString
	 * 		������ �Ͻ�. ����/������ ���Ǹ� ���� 'yyyy-MM-dd HH:mm:ss'������ String���� �޾ƿɴϴ�.
	 * @param univName
	 */
	public void setRecentUpdate(String dateString,String univName){ 
		updateQuery("INSERT INTO updates(time,univName) values('" + dateString + "','"+univName+"');");
//		getRecentUpdate();
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
					"INSERT INTO articles(id,created_time,univName) "+
					"VALUES('" + article.getId() + "','" +
								 article.getCreatedTime() + "', '" +
								 article.getUnivName() + "');"
						);	
		}
		
	}
	
	
	
	
	/**
	 * �б��̸��� �ش��ϴ� ��� ���� id���� DB�� �ش� ���̺�κ��� �ҷ��ɴϴ�.
	 * @param univName
	 * 		��� �б�.
	 * @param tableName
	 * 		DB���� ���̺� ��.
	 */
	public ArrayList<String> readIds(String univName,String tableName){
		ArrayList<String> list = new ArrayList<String>();
		try {			
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("SELECT id FROM "+tableName+" WHERE univName='" + univName + "';");
			if (st.execute("SELECT id FROM "+tableName+" WHERE univName='" + univName + "';")) {
				rs = st.getResultSet();
			}
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		return list;
	}
	
	
	/**
	 * �б��̸��� �ش��ϴ� ��� ���� DB�� �ش� ���̺�κ��� �ҷ��ɴϴ�.
	 * @param univName
	 * 		��� �б�.
	 * @param tableName
	 * 		DB���� ���̺� ��.
	 * @return
	 * 		ArrayList<Article>���·� ��ȯ�մϴ�.
	 */
	public ArrayList<Article> readArticles(String univName,String tableName){
		ArrayList<Article> articles = new ArrayList<Article>();
		try {			
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);

			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM "+tableName+" WHERE univName='" + univName + "';");
			if (st.execute("SELECT * FROM "+tableName+" WHERE univName='" + univName + "';")) {
				rs = st.getResultSet();
			}
			while (rs.next()) {
				Article article = new Article(
						rs.getString("id"),
						rs.getString("created_time"),
						univName,
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
		}
		
	}
	/**
	 *  �б��� ��̵��� ������ ���� ��� �۵��� DB�κ��� �޾ƿ�
	 *  Article�� ArrayList ���·� ��ȯ�մϴ�. 
	 *  <p> DB���� 'articles'���̺��� ������ ��ȯ�մϴ�.
	 * @param univName
	 * 		��� �б�.
	 * @param limitNumber
	 * 		�� �� ����.
	 * @return
	 * 		Article�� ArrayList���·�  ��ȯ.
	 */
	public ArrayList<Article> fetchInterestingArticles(String univName, int limitNumber){
		ArrayList<Article> articles = new ArrayList<Article>();
		try {			
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
			String query = 
					  "SELECT * "
					+ "FROM articles "
					+ "WHERE univName = '" + univName
					+ "' and interesting > "+ Constants.interestingMin
					+ " ORDER BY interesting DESC "
					+ "LIMIT "+limitNumber
					+ ";";
					
			rs = st.executeQuery(query);			
			if (st.execute(query)) {
				rs = st.getResultSet();
			}
			while (rs.next()) {
				Article article = new Article(
						rs.getString("id"),
						rs.getString("created_time"),
						univName,
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
//			System.out.println(article.getId());
//			System.out.println(article.get("created_time"));
//			System.out.println(article.get("univName"));
			String message = article.getMessage();
			if (message!=null){
//				message = " "+message+" ";
				message = message.replaceAll("\'", "��");//��
				message = message.replaceAll("\"", "\\����");
				
			}else
				message = "������ ���� ���Դϴ�.";
			updateQuery(
					"INSERT INTO interestingArticles(id,likes,comments,shares,message,created_time,interesting,univName) "+
					"VALUES('" + article.getId() +"', "
							+ article.getLikes() +", "
							+ article.getComments() +", "
							+ article.getShares() +", '"
							+ message +"', '"
							+ article.getCreatedTime() +"', "
							+ article.getInteresting() +", '"
							+ article.getUnivName() +"');"
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
			Connection con = null;
			java.sql.Statement stmt = null;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,id,pw); 
			stmt = con.createStatement();
			stmt.executeUpdate(q);		
			stmt.close();
			con.close();
		}catch(ClassNotFoundException cnfe){ 
			System.out.println("jdbc mysql Driver�� ã�� �� �����ϴ�."); 
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
