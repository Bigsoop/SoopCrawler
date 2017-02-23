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
	private int univKey;
	private int likes;
	private int comments;
	private int shares;
	private int interesting;
	private String message;
	
	public Article(String id, String createdTime,int univKey){
		this.id = id;
		this.createdTime = createdTime;
		this.univKey = univKey;
		
	}
	public Article(String id, int likes, int comments, int shares){
		this.id = id;
		this.likes = likes;
		this.comments = comments;
		this.shares = shares;
	}
	public Article(String id, String createdTime, int univKey, int interesting,
			int likes, int comments, int shares){
		this.id = id;
		this.createdTime = createdTime;
		this.univKey = univKey;
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
	public int getLikes(){
		return this.likes;
	}
	public int getComments(){
		return this.comments;
	}
	public int getShares(){
		return this.shares;
	}
	public int getUnivKey(){
		return this.univKey;
	}
	public void setMessage(String message){
		this.message = message;
	}
}
