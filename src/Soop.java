import java.util.Date;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;


public class Soop {
	private String accessToken;
	private String appSecret;
	private FacebookClient fbClient;
	private int limit=5000;
	private Jdbc db;

	
	/**
	 * �׼�����ū�� �۽�ũ���ڵ�� fbClient�� �ʱ�ȭ�ϴ� �������Դϴ�.
	 */
	public Soop(String accessToken,String appSecret){
		this.accessToken = accessToken;
		this.appSecret = appSecret;
		fbClient = new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);
		db = new Jdbc(Constants.DBurl,Constants.DBid,Constants.DBpw);
	}

	/**
	 * �׼�����ū���� fbClient�� �ʱ�ȭ�ϴ� �������Դϴ�.
	 */
	public Soop(String accessToken){
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken,Version.LATEST);
		db = new Jdbc(Constants.DBurl,Constants.DBid,Constants.DBpw);
	}

	/**
	 * �׼��� ��ū�� �����մϴ�.
	 * @param accessToken
	 *  	�ٲپ� �� ���ο� �׼��� ��ū�Դϴ�.<p>
	 */
	public void changeAccessToken(String accessToken){
		this.accessToken = accessToken;		
	}

	/**
	 * �����, ���� ������� ������ ��̷ο�� ���� �߰��ϴ� �۾��� �����մϴ�. 
	 * 
	*/
	public int updateInterestingArticles() {
		System.out.println(fbClient.obtainAppAccessToken(appSecret, accessToken));
		Iterator<String> it = Constants.univ.keySet().iterator();
		int numberOfUnivs=0;
		while( it.hasNext() ){        	
			String univName = it.next();
			System.out.println("==========================================");							    
			System.out.println("<"+univName + " ��̷ο� �� �������� ����> ["+(numberOfUnivs+1)+"/"+(Constants.univ.size())+"]");
			addInterestingArticles(univName,Constants.interestingLimit);
			numberOfUnivs++;
		}
		return numberOfUnivs;

	}

	/**
	 * �ֱ������� ũ�Ѹ��� ������ ��쿡 �⺻������ �����մϴ�.<p>
	 * ��� �б��� ����  �ֱ� ������Ʈ ���� ������ ���ο� ���� �޾ƿ���, 
	 * ������� �޾ƿ� ��� �۵��� ��/��/�� ���� ������Ʈ�մϴ�.<p>
	 * �� �б����� ���ο� ���� id�� �޾ƿ� �����صΰ�, id�� �������� '�����+�������'�ʵ带 �־��ݴϴ�.
	 * �׸��� ��������� �������� ���������� �̾� ��̷ο� ���̺� �߰��մϴ�.
	 */	
	public int updateAll() {
		Iterator<String> it = Constants.univ.keySet().iterator();
		int numberOfUnivs=0;
		while( it.hasNext() ){        	
			String univName = it.next();
			System.out.println("==========================================");							    
			System.out.println("<"+univName + " ũ�Ѹ� ����> ["+(numberOfUnivs+1)+"/"+(Constants.univ.size())+"]");
			
			getSimpleArticles(univName);
			getInterestInformations(univName);
			addInterestingArticles(univName,Constants.interestingLimit);
			numberOfUnivs++;
		}
		outPutLog();
		return numberOfUnivs;

	}
	/**
	 * Ư�� ������ �������� ũ�Ѹ��� ������ ��쿡 �⺻������ �����մϴ�.<p>
	 * ��ġ �ֱ� ������Ʈ���ڰ� �׶��ΰ�ó�� ũ�Ѹ��� �����մϴ�.
	 * <p>
	 * ��� �б��� ����  ������ ���� ������ ���ο� ���� �޾ƿ���, 
	 * ������� �޾ƿ� ��� �۵��� ��/��/�� ���� ������Ʈ�մϴ�.<p>
	 * �� �б����� ���ο� ���� id�� �޾ƿ� �����صΰ�, id�� �������� '�����+�������'�ʵ带 �־��ݴϴ�.
	 * �׸��� ��������� �������� ���������� �̾� ��̷ο� ���̺� �߰��մϴ�.
	 * <p>
	 */	
	public int updateAll(String criteriorDateString) {
		Iterator<String> it = Constants.univ.keySet().iterator();
		int numberOfUnivs=0;
		while( it.hasNext() ){        	
			String univName = it.next();
			System.out.println("==========================================");    		    
			System.out.println("<"+univName + " ũ�Ѹ� ����> ["+(numberOfUnivs+1)+"/"+(Constants.univ.size())+"]");
			
			getSimpleArticles(univName,criteriorDateString);
			getInterestInformations(univName);
			addInterestingArticles(univName,Constants.interestingLimit);
			numberOfUnivs++;
		}
		
		return numberOfUnivs;
	}
	
	
	/**
	 * �ش� �б��� ���ο� ���� �Ϻ� �ʼ������� �޾ƿɴϴ�.<p>
	 * �� �б��� �ֱ� ������Ʈ ��¥ ���Ŀ� �ۼ��� ���� id, �ۼ� �Ͻø� �޾ƿ� DB�� �����մϴ�. 
	 * @param univName
	 * 		��� �б���. <p>��� �б��� ���� �޾ƿ��� �����մϴ�.
	 * <p>
	 */	
	private void getSimpleArticles(String univName){
		ArrayList<Article> articles = new ArrayList<Article>();
		String recentUpdateString = db.getRecentUpdate(univName);
		System.out.println("���� ũ�Ѹ� �ð� : " + recentUpdateString);
		Connection<Post> feed;
		feed = 
				fbClient.fetchConnection(Constants.univ.get(univName) + "/posts", Post.class,
						Parameter.with("limit", 50), Parameter.with("fields", "id,created_time"));
					//	���⿡�� �ٷ� since Parameter�� �����Ҽ���������?
//		System.err.println("�׼�����ū!");
		int numberOfFinished=0;
		pLoop:
		for (List<Post> myFeedConnectionPage : feed){
			System.out.println("���� �� �� " + numberOfFinished + "�� �޾ƿ�..."); // (��) 50�� ������ ���(���ٱ��Ѿ��±� �����ɼ�����.)
			for (Post post : myFeedConnectionPage){
				if (post != null){			
					Date recentUpdate = null;
					try {
						recentUpdate = Constants.fm.parse(recentUpdateString);
					} catch (ParseException e) {
						e.printStackTrace();
						System.err.println("�ر������̾�");
					}
					if (numberOfFinished == this.limit || // ���� �̻����� �ʹ� ���� �޾Ұų� 
						post.getCreatedTime().compareTo(recentUpdate) == -1){ // �ֱ� ������Ʈ���� ������ �ö�� ���̸� �׸� �ޱ�.
						db.setRecentUpdate(Constants.fm.format(new Date()),univName);
						System.out.println(univName + " �� �� ��" + numberOfFinished + "�� ũ�Ѹ� �Ϸ�!");
						break pLoop;
					}
					else{
						Article article = new Article(post.getId(),Constants.fm.format(post.getCreatedTime()),univName);
						articles.add(article);
						numberOfFinished++;
						
					}
				}
			}
		}
		System.out.println("�޾ƿ� �ڷḦ DB�� �����ϴ���...");
		db.writeSimpleInformations(articles); // �⺻�������� DB�� ����.
		
		System.out.println("���� ũ�Ѹ� �Ϸ�ð� : "+Constants.fm.format(new Date()));
		System.out.println("------------------------------------------");
	}
	
	
	
	/**
	 * ���� �Ͻ� ������ �ش��б��� ���ο� ���� �޾ƿɴϴ�.<p>
	 * ���Ӱ� �б� �߰��������� ��Ÿ ��Ȳ �߻� ��, �Ķ���͸� ���� �������� '���� ��������' ���� �Ͻ� ���Ŀ� �ۼ��� �ش� �б��� �� id�� DB�� �����ϴ� �޼ҵ��Դϴ�. 
	 * @param date
	 * 		���� �Ͻ�.  � �Ͻ� ���Ŀ� �ۼ��� �۸� �޾ƿ��� �����մϴ�.
	 * @param univName
	 * 		��� �б�. ��� �б��� ���� �޾ƿ��� �����մϴ�.
	 * 
	 */
	private void getSimpleArticles(String univName,String dateString){
		ArrayList<Article> articles = new ArrayList<Article>();
		String criteriorDateString = dateString;	
		System.out.println("���� ũ�Ѹ� �ð� : " + criteriorDateString);
		Connection<Post> feed = 
				fbClient.fetchConnection(Constants.univ.get(univName) + "/posts", Post.class,
						Parameter.with("limit", 50), Parameter.with("fields", "id,created_time"));
					//	���⿡�� �ٷ� since Parameter�� �����Ҽ���������?
		int numberOfFinished=0;
		pLoop:
		for (List<Post> myFeedConnectionPage : feed){
			System.out.println("���� �� �� " + numberOfFinished + "�� �޾ƿ�..."); // (��) 50�� ������ ���(���ٱ��Ѿ��±� �����ɼ�����.)
			for (Post post : myFeedConnectionPage){
				if (post != null){			
					Date creteriorDate = null;
					try {
						creteriorDate = Constants.fm.parse(criteriorDateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (numberOfFinished == this.limit || // ���� �̻����� �ʹ� ���� �޾Ұų� 
						post.getCreatedTime().compareTo(creteriorDate) == -1){ // �ֱ� ������Ʈ���� ������ �ö�� ���̸� �׸� �ޱ�.
						db.setRecentUpdate(Constants.fm.format(new Date()),univName);
						System.out.println(univName + " �� �� ��" + numberOfFinished + "�� ũ�Ѹ� �Ϸ�!");
						break pLoop;
					}
					else{
						articles.add(new Article(post.getId(),Constants.fm.format(post.getCreatedTime()),univName));
						numberOfFinished++;
						
					}
				}
			}
		}
		System.out.println("�޾ƿ� �ڷḦ DB�� �����ϴ���...");
		db.writeSimpleInformations(articles); // �⺻�������� DB�� ����.
		
		System.out.println("���� ũ�Ѹ� �Ϸ�ð� : "+Constants.fm.format(new Date()));
		System.out.println("------------------------------------------");
	}






	/**
	 * DB�� ����� ��� �Խñ� �� ������ �б��� �ش��ϴ� �Խñ��� ���ƿ�, ���, ���� Ƚ���� ������Ʈ�մϴ�.
	 * <p>db���� id�� ���ͼ� graph api������ ��,��,�� �޾Ƽ� db�� update.
	 * @param univName
	 * 		��� �б�. 
	 */
	private void getInterestInformations(String univName){	
		ArrayList<Article> articles = new ArrayList<Article>();		
		System.out.println(univName+" ���ƿ�, ���, ���� �� ������Ʈ ����..");
		ArrayList<String> ids = db.readIds(univName,"articles");
		int numberOfFinished=0;		
		int sizeOfList = ids.size();
		int quotient = (int) ( (sizeOfList-1) / 50)+1; 
//		System.out.println(quotient);
		for(int i=0; i<quotient; i++){
			System.out.println("���� " + numberOfFinished + "�� �޾ƿ�..."); //			
			List<String> subIds = // 50�� �� ������ ��û�ؾ� ��.
					ids.subList(	i*50,	Math.min(i*50+50,sizeOfList)	);
			JsonObject obj=null;
			try{
			obj =  fbClient.fetchObjects(subIds,
					JsonObject.class,
					Parameter.with("fields", "likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
			//	�Ķ���� ���¹� ����� ����
			}
			catch(Exception e){
				System.err.println("�̻�߻�! �Ƹ� ��������������!");
			}
			for(String id: subIds){
				try{
					int likes = obj.getJsonObject(id).getJsonObject("likes").getJsonObject("summary").getInt("total_count");
					int comments = obj.getJsonObject(id).getJsonObject("comments").getJsonObject("summary").getInt("total_count");
					int shares = 0;
					if (obj.getJsonObject(id).has("shares"))
						shares = obj.getJsonObject(id).getJsonObject("shares").getInt("count");
					articles.add(new Article(id,likes,comments,shares));
					numberOfFinished++;				
				}catch(Exception e){ 
					System.err.println("�̻��ѳ� �߰��ߴ�!");
					System.err.println(id); 
					
				}
								
			}
			
		}		
		System.out.println("�޾ƿ� �ڷḦ DB�� �����ϴ���...");
		db.writeInterestInformations(articles);
		System.out.println(univName+" ���ƿ�, ���, ���� �� ���� "+ids.size() +"�� ������Ʈ �Ϸ�!");
		System.out.println("------------------------------------------");
	}

	
	
	
	
	/**
	 * DB�� 'filteredArticles'���̺� ���� �б��� ��̷ο� �۵��� �߰��մϴ�.
	 * <p> db�κ��� �б��� �����۵��� �߷� articles�� �޾ƿ��� 
	 * ���ݿ����� graph api ���� message �޾Ƽ�  
	 * ��ݹ��� articles�� message�ʵ带 ä���ְ�
	 * interestingArticles ���̺� insert. 
	 *  
	 * @param UnivName
	 */
	private void addInterestingArticles(String univName,int limitNumber){
		System.out.println("��̷ο� ���� "+Constants.interestingLimit +"���� �����͸� �߷� �޾ƿ�����...");
		ArrayList<Article> articles = db.fetchInterestingArticles(univName, limitNumber);//db���� (���Ǹ����ϴ�) �ش��б��� ���� n�� �̾ƿ�.
		//graph�� ��û�ؼ� message�޾ƿ�
		List<String> ids = new ArrayList<String>();		
		for(Article article : articles){
			ids.add(article.getId()); //id�� �̾Ƽ� ��û�ϴµ� �� ����.
		}
		JsonObject obj = null;
		try{
		 obj =  fbClient.fetchObjects(ids,
					JsonObject.class,
					Parameter.with("fields", "message"));
		}
		catch(IllegalArgumentException e){
			System.err.println("�� ����Ʈ �����߻�!");
			
		}
		for(String id: ids){
			try{ 
				String message = obj.getJsonObject(id).getString("message");
				for(Article article : articles){					
					if(article.getId()==id){
						article.setMessage(message);
					}
					
				}
			}catch(Exception e){ 
				System.err.println("�޽����� ���� ���Դϴ�.");
				System.err.println(id);
			}
		}
	

		System.out.println("�޾ƿ� �ڷḦ DB�� �����ϴ���...");
		db.writeInterestingArticles(articles);
		System.out.println(univName+" ��̷ο� �ڷ� ���ε� �Ϸ�.");
		
		
	}
	
	
	public void outPutLog(){
		try {
		      //create a buffered reader that connects to the console, we use it so we can read lines
		      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		      //read a line from the console
		      String lineFromInput = in.readLine();

		      //create an print writer for writing to a file
		      PrintWriter out = new PrintWriter(new FileWriter("log/output.txt"));

		      //output to the file a line
		      out.println(lineFromInput);

		      //close the file (VERY IMPORTANT!)
		      out.close();
		   }
		      catch(IOException e1) {
		        System.out.println("Error during reading/writing");
		   }
	}
	
	
	/**
	 * �� �׽�Ʈ���Դϴ�. id�޾Ƽ� ���� ��������� ��ٿ����� 
	 * @param id
	 * 		�Խñ� id.
	 */
	public void printArticle(String id){

		Post post = fbClient.fetchObject(id,
				Post.class,
				Parameter.with("fields", "message,from,to,likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));

		System.out.println("" + post.getMessage());
		System.out.print("Likes:" + post.getLikesCount());
		System.out.print("\tComments:" + post.getCommentsCount());
		System.out.print("\tShares:" + post.getSharesCount());


	}

}
