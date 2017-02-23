import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

import javax.security.sasl.AuthenticationException;

public class SoopMain {

	public static void main(String[] args){
		Date start = new Date();
		//		Soop soop = new Soop(Constants.accessToken);
		Soop soop = new Soop(Constants.accessToken,Constants.appSecret);
		int numberOfUnivs=0;
		
		String time = null;
		if(args.length == 0){
			soop.updateAll();
		}
		else {
			for(int i=0;i<args.length;i++){
				if (args[i].equals("-at")){
					System.out.println("액세스 토큰 업데이트는 아직 미지원..");		
					System.exit(1);
				}
				else if(args[i].equals("-time") && (i+1)<args.length){
					if (args[i+1].charAt(0)!='-'){
						time = args[i+1];
						soop.updateAll(time);
					}
				}
				else if(args[i].equals("-univ") && (i+1)<args.length){
				
					if (args[i+1].charAt(0)!='-'){
						System.exit(1);;;
					}
					System.out.println("대상 학교 지정 기능은 아직 미지원..");		
					System.exit(1);
					
					
					/*Iterator<String> it = Constants.univ.keySet().iterator();
					int numberOfUnivs=0;
					while( it.hasNext() ){        	
						String univName = it.next();
						System.out.println("==========================================");    		    
						System.out.println("<"+univName + " 크롤링 시작> ["+(numberOfUnivs+1)+"/"+(Constants.univ.size())+"]");
						
						getSimpleArticles(univName,criteriorDateString);
						getInterestInformations(univName);
						addInterestingArticles(univName,Constants.interestingLimit);
						numberOfUnivs++;
					}
//					updateResultTable();
					db.closeConnection();
					return numberOfUnivs;*/
				}
//				else if(args[i].substring(0,2).equals("-s")){
//					switch(args[i].charAt(2)){
//					case '1':
//						
//						break;						
//					case '2':
//						
//						break;
//					case '3':
//						
//						break;
//						
//					}
//				
//					
//				
//				}

			}



			//		numberOfUnivs = soop.updateAll("2016-1-1 12:00:00");	
			//		numberOfUnivs = soop.updateInterestingArticles();


		}
		
//		soop.updateAll(time);
		
		
		Date end = new Date();
		System.out.println("==========================================");
		System.out.println("총 "+numberOfUnivs+"개 대학교 작업 완료.");
		System.out.println("현재 시각 : "+Constants.fm.format(new Date()));		
		System.out.println("총 소요시간 : "+(float) (end.getTime()-start.getTime())/24/60+"초");
		System.out.println("==========================================");

	}

}


	//try {
	//	String aa =  soop.getExtendedAccessToken(Constants.accessToken);
	//	System.out.println(aa);
	//} catch (AuthenticationException e) {
	//	// TODO Auto-generated catch block
	//	e.printStackTrace();
	//}

	//	args[].charAt()
	//	.substring
	//	.length
	//	OptsList.add
