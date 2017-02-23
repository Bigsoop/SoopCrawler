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

import javax.security.sasl.AuthenticationException;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;


public class Soop {
	private String accessToken;
	private String appSecret;
	private FacebookClient fbClient;
	private int limit=5000;
	private Jdbc db;

	
	/**
	 * 액세스토큰과 앱시크릿코드로 fbClient를 초기화하는 생성자입니다.
	 */
	public Soop(String accessToken,String appSecret){
		this.accessToken = accessToken;
		this.appSecret = appSecret;
		fbClient = new DefaultFacebookClient(accessToken, appSecret, Version.LATEST);
		db = new Jdbc(Constants.DBurl,Constants.DBid,Constants.DBpw);
	}

	/**
	 * 액세스토큰으로 fbClient를 초기화하는 생성자입니다.
	 */
	public Soop(String accessToken){
		this.accessToken = accessToken;
		fbClient = new DefaultFacebookClient(accessToken,Version.LATEST);
		db = new Jdbc(Constants.DBurl,Constants.DBid,Constants.DBpw);
	}

	/**
	 * 액세스 토큰을 수정합니다.
	 * @param accessToken
	 *  	바꾸어 줄 새로운 액세스 토큰입니다.<p>
	 */
	public void changeAccessToken(String accessToken){
		this.accessToken = accessToken;
//		System.out.println(fbClient.obtainAppAccessToken(appSecret, accessToken));

	}

	

	/**
	 * 현재글, 현재 좋댓공을 가지고 흥미로운글 만을 추가하는 작업을 수행합니다. 
	 * 
	*/
	public int updateInterestingArticles() {		
		int numberOfUnivs=0;
		for(int i=0;i<Constants.univs.length;i++){        	
			int univKey = i;
			System.out.println("==========================================");							    
			System.out.println("<"+Constants.univs[univKey].getName() + " 흥미로운 글 가져오기 시작> ["+(numberOfUnivs+1)+"/"+(Constants.univs.length)+"]");
			addInterestingArticles(univKey,Constants.interestingLimit);
			numberOfUnivs++;
		}
		return numberOfUnivs;

	}

	/**
	 * 주기적으로 크롤링을 수행할 경우에 기본적으로 실행합니다.<p>
	 * 모든 학교에 대해  최근 업데이트 시점 이후의 새로운 글을 받아오고, 
	 * 현재까지 받아온 모든 글들의 좋/댓/공 수를 업데이트합니다.<p>
	 * 각 학교별로 새로운 글의 id를 받아와 저장해두고, id를 기준으로 '좋댓공+흥미지수'필드를 넣어줍니다.
	 * 그리고 흥미지수를 바탕으로 일정개수를 뽑아 흥미로운 테이블에 추가합니다.
	 */	
	public int updateAll() {
		int numberOfUnivs=0;
		for(int i=0;i<Constants.univs.length;i++){        	
			int univKey = i;
			System.out.println("==========================================");							    
			System.out.println("<"+Constants.univs[univKey].getName() + " 크롤링 시작> ["+(numberOfUnivs+1)+"/"+(Constants.univs.length)+"]");
			getSimpleArticles(univKey);
			getInterestInformations(univKey);
			addInterestingArticles(univKey,Constants.interestingLimit);
			numberOfUnivs++;
		}
//		updateResultTable();
//		outPutLog();
		db.closeConnection();
		return numberOfUnivs;

	}
	/**
	 * 특정 시점을 기준으로 크롤링을 수행할 경우에 기본적으로 실행합니다.<p>
	 * 마치 최근 업데이트일자가 그때인것처럼 크롤링을 수행합니다.
	 * <p>
	 * 모든 학교에 대해  정해준 시점 이후의 새로운 글을 받아오고, 
	 * 현재까지 받아온 모든 글들의 좋/댓/공 수를 업데이트합니다.<p>
	 * 각 학교별로 새로운 글의 id를 받아와 저장해두고, id를 기준으로 '좋댓공+흥미지수'필드를 넣어줍니다.
	 * 그리고 흥미지수를 바탕으로 일정개수를 뽑아 흥미로운 테이블에 추가합니다.
	 * <p>
	 */	
	public int updateAll(String criteriorDateString) {
		int numberOfUnivs=0;
		for(int i=0;i<Constants.univs.length;i++){        	
			int univKey = i;
			System.out.println("==========================================");    		    
			System.out.println("<"+Constants.univs[univKey].getName() + " 크롤링 시작> ["+(numberOfUnivs+1)+"/"+(Constants.univs.length)+"]");
			getSimpleArticles(univKey,criteriorDateString);
			getInterestInformations(univKey);
			addInterestingArticles(univKey,Constants.interestingLimit);
			numberOfUnivs++;
		}
//		updateResultTable();
		db.closeConnection();
		return numberOfUnivs;
	}
	
	
	/**
	 * 해당 학교의 새로운 글을 일부 필수정보만 받아옵니다.<p>
	 * 각 학교별 최근 업데이트 날짜 이후에 작성된 글의 id, 작성 일시를 받아와 DB에 저장합니다. 
	 * @param univName
	 * 		대상 학교명. <p>어느 학교의 글을 받아올지 설정합니다.
	 * <p>
	 */	
	private void getSimpleArticles(int univKey){
		ArrayList<Article> articles = new ArrayList<Article>();
		String recentUpdateString = db.getRecentUpdate(univKey);
		System.out.println("이전(최소기준) 크롤링 시각 : " + recentUpdateString);
		Connection<Post> feed;
		feed = 
				fbClient.fetchConnection(Constants.univs[univKey].getUrl() + "/posts", Post.class,
						Parameter.with("limit", 100), Parameter.with("fields", "id,created_time"));
					//	여기에서 바로 since Parameter로 적용할수도있을까?
//		System.err.println("액세스토큰!");
		int numberOfFinished=0;
		pLoop:
		for (List<Post> myFeedConnectionPage : feed){
			System.out.println("현재 새 글 " + numberOfFinished + "개 받아옴..."); // (약) 100개 단위로 출력(접근권한없는글 누락될수있음.)
			for (Post post : myFeedConnectionPage){
				if (post != null){			
					Date recentUpdate = null;
					try {
						recentUpdate = Constants.fm.parse(recentUpdateString);
					} catch (ParseException e) {
						e.printStackTrace();
						System.err.println("준구야좃됫어");
					}
					if (numberOfFinished == this.limit || // 동작 이상으로 너무 많이 받았거나 
						post.getCreatedTime().compareTo(recentUpdate) == -1){ // 최근 업데이트보다 이전에 올라온 글이면 그만 받기.
						//      이글의cretedTime < recentUpdate
						break pLoop;
					}
					else{ //	이글의createdTime >= recentUpdate
						Article article = new Article(post.getId(),Constants.fm.format(post.getCreatedTime()),univKey);
						articles.add(article);
						numberOfFinished++;
						
					}
				}
			}
		}
		
		System.out.println("새 글 총" + numberOfFinished + "개 받아옴!");
		System.out.println("받아온 자료를 DB에 전송하는중...");
		db.writeSimpleInformations(articles); // 기본정보들을 DB에 저장.
		db.setRecentUpdate(Constants.fm.format(new Date()),univKey);
		System.out.println(Constants.univs[univKey].getName() + " 새 글 총" + numberOfFinished + "개 크롤링 완료!");
		
		System.out.println("새 글 DB전송 완료시각 : "+Constants.fm.format(new Date()));
		System.out.println("------------------------------------------");
	}
	
	
	
	/**
	 * 기준 일시 이후의 해당학교의 새로운 글을 받아옵니다.<p>
	 * 새롭게 학교 추가됐을때나 기타 상황 발생 시, 파라미터를 통해 수동으로 '직접 설정해준' 기준 일시 이후에 작성된 해당 학교의 글 id를 DB에 저장하는 메소드입니다. 
	 * @param date
	 * 		기준 일시.  어떤 일시 이후에 작성된 글만 받아올지 설정합니다.
	 * @param univName
	 * 		대상 학교. 어느 학교의 글을 받아올지 설정합니다.
	 * 
	 */
	private void getSimpleArticles(int univKey,String dateString){
		ArrayList<Article> articles = new ArrayList<Article>();
		String criteriorDateString = dateString;	
		System.out.println("기준 크롤링 시각 : " + criteriorDateString);
		Connection<Post> feed = 
				fbClient.fetchConnection(Constants.univs[univKey].getUrl() + "/posts", Post.class,
						Parameter.with("limit", 100), Parameter.with("fields", "id,created_time"));
					//	여기에서 바로 since Parameter로 적용할수도있을까?
		int numberOfFinished=0;
		pLoop:
		for (List<Post> myFeedConnectionPage : feed){
			System.out.println("현재 새 글 " + numberOfFinished + "개 받아옴..."); // (약) 100개 단위로 출력(접근권한없는글 누락될수있음.)
			for (Post post : myFeedConnectionPage){
				if (post != null){			
					Date creteriorDate = null;
					try {
						creteriorDate = Constants.fm.parse(criteriorDateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (numberOfFinished == this.limit || // 동작 이상으로 너무 많이 받았거나 
						post.getCreatedTime().compareTo(creteriorDate) == -1){ // if< 최근 업데이트보다 이전에 올라온 글이면 그만 받기.
						// cret >DB읽or기본값
						break pLoop;
					}
					else{ // cret <=DB읽or기본값
						articles.add(new Article(post.getId(),Constants.fm.format(post.getCreatedTime()),univKey));
						numberOfFinished++;
						
					}
				}
			}
		}
		System.out.println("새 글 총" + numberOfFinished + "개 받아옴!");
		System.out.println("받아온 자료를 DB에 전송하는중...");
		db.writeSimpleInformations(articles); // 기본정보들을 DB에 저장.
		db.setRecentUpdate(Constants.fm.format(new Date()),univKey);
		System.out.println(Constants.univs[univKey].getName() + " 새 글 총" + numberOfFinished + "개 크롤링 완료!");		
				
		
		System.out.println("새 글 DB전송 완료시각 : "+Constants.fm.format(new Date()));
		System.out.println("------------------------------------------");
	}






	/**
	 * DB에 저장된 모든 게시글 중 지정한 학교에 해당하는 게시글의 좋아요, 댓글, 공유 횟수를 업데이트합니다.
	 * <p>db에서 id만 빼와서 graph api돌려서 좋,댓,공 받아서 db에 update.
	 * @param univName
	 * 		대상 학교. 
	 */
	private void getInterestInformations(int univKey){	
		ArrayList<Article> articles = new ArrayList<Article>();		
		System.out.println(Constants.univs[univKey].getName()+" 좋아요, 댓글, 공유 수 업데이트 시작..");
		ArrayList<String> ids = db.readIds(univKey,"articles");
		int numberOfFinished=0;		
		int sizeOfList = ids.size();
		int quotient = (int) ( (sizeOfList-1) / 50)+1; 
//		System.out.println(quotient);
		for(int i=0; i<quotient; i++){
			System.out.println("현재 " + numberOfFinished + "개 받아옴..."); //			
			List<String> subIds = // 50개 씩 나눠서 요청해야 함.
					ids.subList(	i*50,	Math.min(i*50+50,sizeOfList)	);
			JsonObject obj=null;
			try{
			obj =  fbClient.fetchObjects(subIds,
					JsonObject.class,
					Parameter.with("fields", "reactions.summary(true).limit(0),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
			//	파라미터 쓰는법 제대로 알자
			}
			catch(Exception e){
				System.err.println("이상발생! 아마 망한페이지같다!");
			}
			for(String id: subIds){
				try{ //@@ like말고 다른거도 받아와야지 화나요 멋져요 같은거
					int likes = obj.getJsonObject(id).getJsonObject("reactions").getJsonObject("summary").getInt("total_count"); //reaction emotion haha sad happy...
					int comments = obj.getJsonObject(id).getJsonObject("comments").getJsonObject("summary").getInt("total_count");
					int shares = 0;
					
					if (obj.getJsonObject(id).has("shares"))
						shares = obj.getJsonObject(id).getJsonObject("shares").getInt("count");
					articles.add(new Article(id,likes,comments,shares));
					numberOfFinished++;				
				}catch(Exception e){ 
					System.err.println("이상한놈 발견했다!");
					System.err.println(id); 
					
				}
								
			}
			
		}		
		System.out.println("받아온 자료를 DB에 전송하는중...");
		db.writeInterestInformations(articles);
		System.out.println(Constants.univs[univKey].getName()+" 좋아요, 댓글, 공유 수 각각 "+ids.size() +"개 업데이트 완료!");
		System.out.println("------------------------------------------");
	}

	
	
	
	
	/**
	 * DB의 'filteredArticles'테이블에 현재 학교의 흥미로운 글들을 추가합니다.
	 * <p> db로부터 학교별 상위글들을 추려 articles로 받아오고 
	 * 지금에서야 graph api 통해 message 받아서  
	 * 방금받은 articles의 message필드를 채워주고
	 * interestingArticles 테이블에 insert. 
	 *  
	 * @param UnivName
	 */
	private void addInterestingArticles(int univKey,int limitNumber){
		System.out.println("흥미로운 상위 "+limitNumber +"개의 데이터를 추려 받아오는중...");
		//페이지 구독수 받아오기
		limitNumber = 9;//동적으로 처리해야돼. 
		ArrayList<Article> articles = db.fetchInterestingArticles(univKey, limitNumber);//db에서 (조건만족하는) 해당학교의 상위 n개 뽑아옴.
		//graph에 요청해서 message받아와
		List<String> ids = new ArrayList<String>();		
		for(Article article : articles){
			ids.add(article.getId()); //id만 뽑아서 요청하는데 쓸 것임.
		}
		JsonObject obj = null;
		try{
		 obj =  fbClient.fetchObjects(ids,
					JsonObject.class,
					Parameter.with("fields", "message"));
		}
		catch(IllegalArgumentException e){
			System.err.println("빈 리스트 오류발생!");
			
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
				System.err.println("메시지가 없는 글입니다.");
				System.err.println(id);
			}
		}
	

		System.out.println("받아온 자료를 DB에 전송하는중...");
		db.writeInterestingArticles(articles);
		System.out.println(Constants.univs[univKey].getName()+" 흥미로운 자료 업로드 완료.");
		
		
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
	 * Exchange the short lived token (2h) for a long lived one (60j).
	 * 
	 * @param accessToken Short lived token
	 * @return Long lived token
	 * @throws AuthenticationException Invalid token
	 */
	public String getExtendedAccessToken(String accessToken) throws AuthenticationException {
	    
	    AccessToken extendedAccessToken = null;
	    try {
	        extendedAccessToken = fbClient.obtainExtendedAccessToken(Constants.appId, Constants.appSecret, Constants.accessToken);

//	        if (log.isDebugEnabled()) {
//	            log.debug(MessageFormat.format("Got long lived session token {0} for token {1}", extendedAccessToken, accessToken));
//	        }
	        
	    } catch (FacebookException e) {
	        if (e.getMessage().contains("Error validating access token")) {
	            throw new AuthenticationException("Error validating access token");
	        }
	            
	        throw new RuntimeException("Error exchanging short lived token for long lived token", e);
	    }
	    return extendedAccessToken.getAccessToken();
	}
	
	private void updateResultTable(){
		db.updateWeekTable();
		db.updateBestTable();
	}
	
	
	
	/**
	 * 걍 테스트용입니당. id받아서 내용 보고싶을때 써바영ㅎㅎ 
	 * @param id
	 * 		게시글 id.
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
