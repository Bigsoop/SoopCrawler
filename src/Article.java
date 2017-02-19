/**
 * ��Ʈ�ѷ��� �𵨰��� �Խñ� ������ ������ �ְ� ���� �� ����ϱ� ���� Ŭ����.
 * <p>
 * Article Ŭ������ DB���� article�� �ʵ� ������ ����������,
 * �� �ʿ��� ��������� �־ ������ �ְ�����Ƿ�, 
 * �־����� ���� ��������� null ���̴�.
 * �׷��Ƿ� �� ��ü�� ���� DB�� �ϳ��� ���ڵ�� �ݵ�� ��ġ�ϴ� ���� �ƴϴ�.  
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
