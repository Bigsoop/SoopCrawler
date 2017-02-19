/**
 * 컨트롤러와 모델간의 게시글 단위로 정보를 주고 받을 때 사용하기 위한 클래스.
 * <p>
 * Article 클래스는 DB상의 article와 필드 구성은 동일하지만,
 * 꼭 필요한 멤버변수만 넣어서 정보를 주고받으므로, 
 * 넣어주지 않은 멤버변수는 null 값이다.
 * 그러므로 그 자체가 실제 DB의 하나의 레코드와 반드시 일치하는 것은 아니다.  
 * @author Joon
 *
 */
public class Article {

	private String id;
	private String createdTime;
	private String univName;
	private int likes;
	private int comments;
	private int shares;
	private int interesting;
	private String message;
	
	public Article(String id, String createdTime,String univName){
		this.id = id;
		this.createdTime = createdTime;
		this.univName = univName;
		
	}
	public Article(String id, int likes, int comments, int shares){
		this.id = id;
		this.likes = likes;
		this.comments = comments;
		this.shares = shares;
	}
	public Article(String id, String createdTime, String univName, int interesting,
			int likes, int comments, int shares){
		this.id = id;
		this.createdTime = createdTime;
		this.univName = univName;
		this.interesting = interesting;
		this.likes = likes;
		this.comments = comments;
		this.shares = shares;
		
	}
	public String getMessage(){
		return this.message;
	}
	public int getInteresting(){
		return this.interesting;		
	}
	public String getId(){
		return this.id;
	}
	public String getCreatedTime(){
		return this.createdTime;
	}
	public String getUnivName(){
		return this.univName;
	}
	public int getLikes(){
		return this.likes;
	}
	public int getComments(){
		return this.comments;
	}
	public int getShares(){
		return this.shares;
	}
	public void setMessage(String message){
		this.message = message;
	}
}
