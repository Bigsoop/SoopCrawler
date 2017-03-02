
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;



public class Jdbc {
	private String url;
	private String id;
	private String pw;
	private Connection con;
	
	
	/**
	 * DB접근에 필요한 정보를 초기화하는 생성자입니다.
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
	 * 최근 업데이트 일시를 DB로부터 불러옵니다.
	 * @param univKey
	 * 		대상 학교번호.  <p>어느 학교의 업데이트 일시를 받아올지 지정합니다.  
	 * @return 
	 * 		최근 업데이트 일시를 String형태로 반환합니다.<p> 
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
	 *  최근 업데이트 일시를 인자로 받아 DB에 저장합니다.
	 * @param dateString
	 * 		저장할 일시. 저장/관리의 편의를 위해 'yyyy-MM-dd HH:mm:ss'형태의 String으로 받아옵니다.
	 * @param univKey
	 * 		대상 학교번호.
	 */
	public void setRecentUpdate(String dateString, int univKey){
		if (getRecentUpdate(univKey).equals(Constants.defaultRecentUpdate))
			updateQuery("INSERT INTO recentUpdates(time,univKey) values('" + dateString + "',"+univKey+");");
		else
			updateQuery("UPDATE recentUpdates SET time='"+dateString+"', univKey="+univKey+" WHERE univKey="+univKey+";");
	}
	/**
	 *  최근 업데이트 일시를 현재시간으로하여 DB에 저장합니다.
	 * @param univKey
	 * 		대상 학교번호.
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
	 * 새 글들의 간단한 정보를 DB에 저장합니다.<p>
	 * 여기에서는 id와 작성 일시, 학교명만 저장합니다.
	 * @param createdTime
	 * 		작성 일시.
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
	 * 학교이름에 해당하는 모든 글의 id만을 DB의 해당 테이블로부터 불러옵니다.
	 * @param univKey
	 * 		대상 학교번호.
	 * @param tableName
	 * 		DB상의 테이블 명.
	 */
	public ArrayList<String> readIds(int univKey,String tableName){
		ArrayList<String> list = new ArrayList<String>();
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Constants.DBurl,Constants.DBid,Constants.DBpw);			
			
			java.sql.Statement st = null;
			ResultSet rs = null;
			st = con.createStatement();
//			String 지금보다6개월전 = Constants.fm.format(new Date()) new Date());
			rs = st.executeQuery("SELECT id FROM "+tableName+
					" WHERE univKey=" + univKey + ";"); 
//					" and unix_timestamp(created_time) > unix_timestamp('"+지금보다6개월전+"') ;"); 
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
	 * 학교이름에 해당하는 모든 글을 DB의 해당 테이블로부터 불러옵니다.
	 * @param univKey
	 * 		대상 학교번호.
	 * @param tableName
	 * 		DB상의 테이블 명.
	 * @return
	 * 		ArrayList<Article>형태로 반환합니다.
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
	 * 인자로 받은 게시글 리스트 내의 모든 게시글의 좋아요,댓글,공유 횟수를 DB에 업데이트합니다.<p>
	 * @param articles
	 * 		게시글을 ArrayList로 받아옵니다.
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
	 *  학교별 흥미도로 정렬한 상위 몇개의 글들을 DB로부터 받아와
	 *  Article의 ArrayList 형태로 반환합니다. 
	 *  <p> DB상의 'articles'테이블에서 가져와 반환합니다.
	 *  <p> 최근 30일동안 일정수준넘은 상위 n개.
	 * @param univKey
	 * 		대상 학교번호.
	 * @param limitNumber
	 * 		글 수 제한.
	 * @return
	 * 		Article의 ArrayList형태로  반환.
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
	 *  학교별 흥미도로 정렬한 상위 몇개의 글들을 DB로부터 받아와
	 *  Article의 ArrayList 형태로 반환합니다. 
	 *  <p> 개수기준이 아니고 일정수준 넘은거 가져옴.
	 *  <p> DB상의 'articles'테이블에서 가져와 반환합니다.
	 * @param univKey
	 * 		대상 학교번호.
	 * 
	 * @return
	 * 		Article의 ArrayList형태로  반환.
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
	 * 흥미로운 글들을 DB에 저장합니다. 
	 * <p> interestingArticles 테이블에 저장합니다. 
	 * @param articles
	 * 		저장할 글은 Article의 ArrayList(articles)형태로 지정합니다.
	 */
			
	public void writeInterestingArticles(ArrayList<Article> articles){
		
		for (Article article : articles){
			String message = article.getMessage();
			if (message!=null){
				message = message.replaceAll("\'", "‘");//‘
				message = message.replaceAll("\"", "\\‘‘");
				
			}else
				message = "내용이 없는 글입니다.";
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
	 * DB에 select요청을 제외한, update,delete,insert요청의 SQL문을 전달합니다. 
	 * @param q
	 * 		String 형태의 SQL문입니다.	  
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


// write는 DB에 내용들을 잔뜩 쓰는거라서 write.
// read는 DB에서 내용들을 잔뜩 읽는거라서 read.
// DB접근이지만 get,set은 (대학별) 단일 값 설정이라 get,set으로했음.  
