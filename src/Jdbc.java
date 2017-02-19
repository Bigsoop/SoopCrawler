
import java.sql.*;
import java.util.ArrayList;



public class Jdbc {
	private String url;
	private String id;
	private String pw;
	private static Connection con=null;
	
	
	/**
	 * DB접근에 필요한 정보를 초기화하는 생성자입니다.
	 */
	public Jdbc(String url, String id, String pw){
		this.url = url;
		this.id = id;
		this.pw = pw;
	}

	
	
	/**
	 * 최근 업데이트 일시를 DB로부터 불러옵니다.
	 * @param univName
	 * 		대상 학교명.  <p>어느 학교의 업데이트 일시를 받아올지 지정합니다.  
	 * @return 
	 * 		최근 업데이트 일시를 String형태로 반환합니다.<p> 
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
	 *  최근 업데이트 일시를 인자로 받아 DB에 저장합니다.
	 * @param dateString
	 * 		저장할 일시. 저장/관리의 편의를 위해 'yyyy-MM-dd HH:mm:ss'형태의 String으로 받아옵니다.
	 * @param univName
	 */
	public void setRecentUpdate(String dateString,String univName){ 
		updateQuery("INSERT INTO updates(time,univName) values('" + dateString + "','"+univName+"');");
//		getRecentUpdate();
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
					"INSERT INTO articles(id,created_time,univName) "+
					"VALUES('" + article.getId() + "','" +
								 article.getCreatedTime() + "', '" +
								 article.getUnivName() + "');"
						);	
		}
		
	}
	
	
	
	
	/**
	 * 학교이름에 해당하는 모든 글의 id만을 DB의 해당 테이블로부터 불러옵니다.
	 * @param univName
	 * 		대상 학교.
	 * @param tableName
	 * 		DB상의 테이블 명.
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
	 * 학교이름에 해당하는 모든 글을 DB의 해당 테이블로부터 불러옵니다.
	 * @param univName
	 * 		대상 학교.
	 * @param tableName
	 * 		DB상의 테이블 명.
	 * @return
	 * 		ArrayList<Article>형태로 반환합니다.
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
		}
		
	}
	/**
	 *  학교별 흥미도로 정렬한 상위 몇개의 글들을 DB로부터 받아와
	 *  Article의 ArrayList 형태로 반환합니다. 
	 *  <p> DB상의 'articles'테이블에서 가져와 반환합니다.
	 * @param univName
	 * 		대상 학교.
	 * @param limitNumber
	 * 		글 수 제한.
	 * @return
	 * 		Article의 ArrayList형태로  반환.
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
	 * 흥미로운 글들을 DB에 저장합니다. 
	 * <p> interestingArticles 테이블에 저장합니다. 
	 * @param articles
	 * 		저장할 글은 Article의 ArrayList(articles)형태로 지정합니다.
	 */
			
	public void writeInterestingArticles(ArrayList<Article> articles){
		
		for (Article article : articles){
//			System.out.println(article.getId());
//			System.out.println(article.get("created_time"));
//			System.out.println(article.get("univName"));
			String message = article.getMessage();
			if (message!=null){
//				message = " "+message+" ";
				message = message.replaceAll("\'", "‘");//‘
				message = message.replaceAll("\"", "\\‘‘");
				
			}else
				message = "내용이 없는 글입니다.";
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
	 * DB에 select요청을 제외한, update,delete,insert요청의 SQL문을 전달합니다. 
	 * @param q
	 * 		String 형태의 SQL문입니다.	  
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
			System.out.println("jdbc mysql Driver를 찾을 수 없습니다."); 
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
