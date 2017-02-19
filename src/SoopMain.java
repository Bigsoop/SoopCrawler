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
		System.out.println("�� "+numberOfUnivs+"�� ���б� ũ�Ѹ� �Ϸ�.");
		System.out.println("���� �ð� : "+Constants.fm.format(new Date()));		
		System.out.println("�� �ҿ�ð� : "+(float) (end.getTime()-start.getTime())/24/60+"��");
		System.out.println("==========================================");
		
	}
}