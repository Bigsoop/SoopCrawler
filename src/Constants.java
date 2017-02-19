import java.text.SimpleDateFormat;
import java.util.HashMap;

public final class Constants {
	public static final String DBurl = "jdbc:mysql://localhost/soop";
	public static final String DBid = "root";
	public static final String DBpw = "autoset";
	public static final String accessToken = "EAACEdEose0cBAMZACIjAieavoUYPq1XuLmH4fiaV65cd5wNkGAiyEEfAyDgZB5CYubRV2GhV2ITOKIK9UEf1Q6Clcp1FlnPLdAwXZBENC8qZCwnhTqLi5fZCUQC1JRyJpUEX66QjwC5XW8bT1L2xhIYb4Nfgls6OZAmURMyea4DuHcqZBIFTxxLTyGxtpV5r5sZD";
	public static final String appId = "1196677553719613";
	public static final String appSecret = "11f905b816f83e3342233d7b7c3055df";
	public static final String defaultRecentUpdate = "2017-01-01 00:00:00";
	public static final int interestingLimit = 7;
	public static final int interestingMin = 60;
	public static final SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//.0?
	
	public static final HashMap<String, String> univ;
	static
	{
		univ = new HashMap<String, String>();
		univ.put("한국항공대학교","HangughangGongdaehaggyo");
		univ.put("서울대학교", "SNUBamboo");
		univ.put("연세대학교", "yonseibamboo");
		univ.put("고려대학교", "koreabamboo");
		univ.put("서강대학교", "sogangbamboo");
		univ.put("성균관대학교", "SKKUBamboo");
		univ.put("한양대학교", "hyubamboo");
		univ.put("중앙대학교", "caubamboo");
		univ.put("경희대학교", "kyungheebamboo");
		univ.put("한국외국어대학교", "hufsbamboo"); //globalbamboo 외대블로벌캠
		univ.put("서울시립대학교", "uosbamboo");
		univ.put("숭실대학교", "sstt2015");
		univ.put("충남대학교", "ChungNamNationalBamboo");
		univ.put("아주대학교", "ajoubamboo");
		univ.put("동국대학교", "donggukbamboo");
		univ.put("명지대학교", "mjubamboo");
		univ.put("세종대학교", "sejongbamboo");
		univ.put("한국교통대학교", "KnutBambooForest");
		univ.put("서울여자대학교", "swubamboo");		
		univ.put("유니스트", "unibamboooo0");
		univ.put("광운대학교", "kwubamboo");
		univ.put("단국대학교", "dkubamboo");
		univ.put("서울과학기술대학교", "seoultechbamboo");
		univ.put("홍익대학교", "hongikbamboo");
		univ.put("상명대학교", "smubb");
		univ.put("인하대학교", "inhabamboo2");
		univ.put("충북대학교", "chungbuknubamboo");
		univ.put("동국대학교", "donggukbamboo");
		univ.put("전북대학교", "jbnusay");
		univ.put("한양대에리카", "ericadruwa");
		univ.put("인천대학교", "incheonbamboo");
		univ.put("서울교육대학교", "snuebamboo");
		univ.put("포항공과대학교", "postechbamboo");
		univ.put("순천향대학교", "schubamboo");
		univ.put("숙명여자대학교", "bamboosmwu");
		univ.put("지스트", "GISTIT.ac.kr");
		univ.put("가톨릭대학교", "CUKbby");
		univ.put("가천대학교", "gcubamboo");
		univ.put("경기대학교", "kyonggibamboo");
		univ.put("국립경상대학교", "gnubamboo1"); //???
		univ.put("건국대학교", "konkukbamboo");
		univ.put("영남대학교", "yubamboo.net");
		univ.put("경북대학교", "KNUbamboo");
		univ.put("전남대학교", "MaeGuemI");
		univ.put("동아대학교", "dongabamboo");
		univ.put("부경대학교", "PKNUBamboo");
		univ.put("성신여자대학교", "SungshinBamboo");
		univ.put("공주대학교", "koungju15");
		univ.put("한성대학교","HSUGrove");
		univ.put("서경대학교","SeoKyeongUnivBamboo");
		univ.put("용인대학교", "yonginbamboo");
		univ.put("순천대학교", "SCNUbamboo");
		univ.put("경북대학교", "KNUbamboo");
		univ.put("한세대학교", "hanseibamboo");
		univ.put("강남대학교", "knubambooforest");
		univ.put("성결대학교", "SKUBAMBOO");
		univ.put("부경대학교", "PKNUBamboo");
		univ.put("성공회대학교", "skhubamboo");
		univ.put("청주대학교", "CJUbambooo");
		univ.put("울산대학교", "ulsanbamboo");
		univ.put("군산대학교", "kunsanspeak");
		univ.put("호남대학교", "HonamUniversityBambooGrove");
		univ.put("세명대학교", "SMUBomboo");
		univ.put("부산대학교", "pnubamboo");
		univ.put("삼육대학교", "syubamboo");
		univ.put("안동대학교", "ANUbamboo");
		univ.put("계명대학교", "bamboo1kmu");
		univ.put("목포대학교", "MNUBamboo");
		univ.put("신한대학교", "shinhan001");
//		univ.put("한동대학교", "한동대학교-대나무숲-1713654792215496");
		univ.put("한밭대학교", "HNUbamboo");
		univ.put("건양대학교", "kyuforest");
		univ.put("덕성여자대학교", "dssaytomeanything");
		univ.put("강원대학교", "KANGWONbamboo");
		univ.put("동아방송예술대학교", "dongdeasup");
		univ.put("금오공과대학교", "daenamookumoh");
		univ.put("경인교육대학교", "ginuebamboo");
//		univ.put("동덕여자대학교", "동덕여대-대나무숲-1431832823796835");
		univ.put("을지대학교", "euljibamboo");
		univ.put("경일대학교", "KIUBABOO");
		univ.put("한국예술종합학교", "kartsbamboo");
//		univ.put("동서대학교", "동서대-대나무숲-859759640748426");
		univ.put("카이스트", "kaistbamboofp");
		univ.put("동덕여자대학교", "dongdukbamboo");
		univ.put("부산외국어대학교", "BUFSbamboo");
		univ.put("상지대학교", "dailysangji");
		univ.put("서울예술대학교", "realcommunicatedsia");
		univ.put("명지대학교", "MJbamboo");
		univ.put("한양여자대학교", "hywubamboo");
//		univ.put("동의대학교", "동의대학교-대나무숲-420588408103211");
//		Sub nddd()
//		For i = 0 To 40
//		    a = Split([d1].Offset(i, 0), " ")
//		    [a1].Offset(i, 0) = "univ.put(""" & a(0) & """, """ & a(1) & """);"
//		Next
//		End Sub


		
	}
}
