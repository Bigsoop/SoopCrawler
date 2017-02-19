import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

public class SoopMain {
	
	public static void main(String[] args){
		Date start = new Date();
		Soop soop = new Soop(Constants.accessToken);
//		Soop soop = new Soop(Constants.accessToken,Constants.appSecret);
		
		int numberOfUnivs=0;
//		numberOfUnivs = soop.updateAll("2016-1-1 12:00:00");	
		numberOfUnivs = soop.updateAll();
//		numberOfUnivs = soop.updateInterestingArticles();
		Date end = new Date();
		System.out.println("==========================================");
		System.out.println("총 "+numberOfUnivs+"개 대학교 크롤링 완료.");
		System.out.println("현재 시각 : "+Constants.fm.format(new Date()));		
		System.out.println("총 소요시간 : "+(float) (end.getTime()-start.getTime())/24/60+"초");
		System.out.println("==========================================");
		
	}
}