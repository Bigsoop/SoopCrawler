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
		univ.put("�ѱ��װ����б�","HangughangGongdaehaggyo");
		univ.put("������б�", "SNUBamboo");
		univ.put("�������б�", "yonseibamboo");
		univ.put("������б�", "koreabamboo");
		univ.put("�������б�", "sogangbamboo");
		univ.put("���հ����б�", "SKKUBamboo");
		univ.put("�Ѿ���б�", "hyubamboo");
		univ.put("�߾Ӵ��б�", "caubamboo");
		univ.put("������б�", "kyungheebamboo");
		univ.put("�ѱ��ܱ�����б�", "hufsbamboo"); //globalbamboo �ܴ��ι�ķ
		univ.put("����ø����б�", "uosbamboo");
		univ.put("���Ǵ��б�", "sstt2015");
		univ.put("�泲���б�", "ChungNamNationalBamboo");
		univ.put("���ִ��б�", "ajoubamboo");
		univ.put("�������б�", "donggukbamboo");
		univ.put("�������б�", "mjubamboo");
		univ.put("�������б�", "sejongbamboo");
		univ.put("�ѱ�������б�", "KnutBambooForest");
		univ.put("���￩�ڴ��б�", "swubamboo");		
		univ.put("���Ͻ�Ʈ", "unibamboooo0");
		univ.put("������б�", "kwubamboo");
		univ.put("�ܱ����б�", "dkubamboo");
		univ.put("������б�����б�", "seoultechbamboo");
		univ.put("ȫ�ʹ��б�", "hongikbamboo");
		univ.put("�����б�", "smubb");
		univ.put("���ϴ��б�", "inhabamboo2");
		univ.put("��ϴ��б�", "chungbuknubamboo");
		univ.put("�������б�", "donggukbamboo");
		univ.put("���ϴ��б�", "jbnusay");
		univ.put("�Ѿ�뿡��ī", "ericadruwa");
		univ.put("��õ���б�", "incheonbamboo");
		univ.put("���ﱳ�����б�", "snuebamboo");
		univ.put("���װ������б�", "postechbamboo");
		univ.put("��õ����б�", "schubamboo");
		univ.put("�����ڴ��б�", "bamboosmwu");
		univ.put("����Ʈ", "GISTIT.ac.kr");
		univ.put("���縯���б�", "CUKbby");
		univ.put("��õ���б�", "gcubamboo");
		univ.put("�����б�", "kyonggibamboo");
		univ.put("���������б�", "gnubamboo1"); //???
		univ.put("�Ǳ����б�", "konkukbamboo");
		univ.put("�������б�", "yubamboo.net");
		univ.put("��ϴ��б�", "KNUbamboo");
		univ.put("�������б�", "MaeGuemI");
		univ.put("���ƴ��б�", "dongabamboo");
		univ.put("�ΰ���б�", "PKNUBamboo");
		univ.put("���ſ��ڴ��б�", "SungshinBamboo");
		univ.put("���ִ��б�", "koungju15");
		univ.put("�Ѽ����б�","HSUGrove");
		univ.put("������б�","SeoKyeongUnivBamboo");
		univ.put("���δ��б�", "yonginbamboo");
		univ.put("��õ���б�", "SCNUbamboo");
		univ.put("��ϴ��б�", "KNUbamboo");
		univ.put("�Ѽ����б�", "hanseibamboo");
		univ.put("�������б�", "knubambooforest");
		univ.put("������б�", "SKUBAMBOO");
		univ.put("�ΰ���б�", "PKNUBamboo");
		univ.put("����ȸ���б�", "skhubamboo");
		univ.put("û�ִ��б�", "CJUbambooo");
		univ.put("�����б�", "ulsanbamboo");
		univ.put("������б�", "kunsanspeak");
		univ.put("ȣ�����б�", "HonamUniversityBambooGrove");
		univ.put("������б�", "SMUBomboo");
		univ.put("�λ���б�", "pnubamboo");
		univ.put("�������б�", "syubamboo");
		univ.put("�ȵ����б�", "ANUbamboo");
		univ.put("�����б�", "bamboo1kmu");
		univ.put("�������б�", "MNUBamboo");
		univ.put("���Ѵ��б�", "shinhan001");
//		univ.put("�ѵ����б�", "�ѵ����б�-�볪����-1713654792215496");
		univ.put("�ѹ���б�", "HNUbamboo");
		univ.put("�Ǿ���б�", "kyuforest");
		univ.put("�������ڴ��б�", "dssaytomeanything");
		univ.put("�������б�", "KANGWONbamboo");
		univ.put("���ƹ�ۿ������б�", "dongdeasup");
		univ.put("�ݿ��������б�", "daenamookumoh");
		univ.put("���α������б�", "ginuebamboo");
//		univ.put("�������ڴ��б�", "��������-�볪����-1431832823796835");
		univ.put("�������б�", "euljibamboo");
		univ.put("���ϴ��б�", "KIUBABOO");
		univ.put("�ѱ����������б�", "kartsbamboo");
//		univ.put("�������б�", "������-�볪����-859759640748426");
		univ.put("ī�̽�Ʈ", "kaistbamboofp");
		univ.put("�������ڴ��б�", "dongdukbamboo");
		univ.put("�λ�ܱ�����б�", "BUFSbamboo");
		univ.put("�������б�", "dailysangji");
		univ.put("���￹�����б�", "realcommunicatedsia");
		univ.put("�������б�", "MJbamboo");
		univ.put("�Ѿ翩�ڴ��б�", "hywubamboo");
//		univ.put("���Ǵ��б�", "���Ǵ��б�-�볪����-420588408103211");
//		Sub nddd()
//		For i = 0 To 40
//		    a = Split([d1].Offset(i, 0), " ")
//		    [a1].Offset(i, 0) = "univ.put(""" & a(0) & """, """ & a(1) & """);"
//		Next
//		End Sub


		
	}
}
