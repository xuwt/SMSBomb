package com.xuwt.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyCode {
	private static int get136EVerifyCode(String paramString) {
		HashMap localHashMap1 = new HashMap();
		localHashMap1
				.put("User-agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C)");
		localHashMap1.put("Referer",
				"http://e.mail.163.com/mobilemail/home.do?from=reg_123");
		String str = parseString(
				"JSESSIONID=(.*?);",
				HttpNetUtil
						.httpRequestHeader(
								"http://e.mail.163.com/mobilemail/home.do?from=reg_123",
								"UTF-8", "Set-Cookie"));
		localHashMap1
				.put("Cookie",
						"__ntes__test__cookies=1343542268131; JSESSIONID="
								+ str
								+ ";_ntes_nnid=59b044401e0122bb5d0aff47b95f1267,1343541362850; _ntes_nuid=59b044401e0122bb5d0aff47b95f1267");
		HttpNetUtil.httpRequest(
				"http://e.mail.163.com/mobilemail/checkMobileBinded.do?mobile="
						+ paramString + "&rnd=0.12959911332059254", "GBK",
				localHashMap1);
		HashMap localHashMap2 = new HashMap();
		localHashMap2.put("Referer",
				"http://e.mail.163.com/mobilemail/forwardToBind.do");
		localHashMap2
				.put("Cookie",
						"__ntes__test__cookies=1343481204572; JSESSIONID="
								+ str
								+ "; _ntes_nnid=59b044401e0122bb5d0aff47b95f1267,1343480563045; _ntes_nuid=59b044401e0122bb5d0aff47b95f1227");
		if (HttpNetUtil
				.httpRequest(
						"http://e.mail.163.com/mobilemail/getVerifyCode.do?rnd=0.40006030573551576",
						"GBK", localHashMap2).contains("result:true"))
			return 1;
		return 0;
	}

	private static int get163VerifyCode(String paramString) {
		if (HttpNetUtil.httpPostRequest(
				"http://reg.email.163.com/mailregAll/sendvcode.do",
				"utf-8",
				"username=" + paramString + "&domain=163.com&mobile="
						+ paramString
						+ "&rand=0.2876529944142271&from=mobilereg").contains(
				"success"))
			return 1;
		return 0;
	}

	private static int get91VerifyCode(String paramString) {
		String str = "http://mobile.91.com/Services/Controller.ashx?callback=jQuery164028059828860001384_1343785185886&action=smstransfer&phone="
				+ paramString + "&cs=gb2312&_=1343785448165";
		HashMap localHashMap = new HashMap();
		localHashMap.put("Referer", "http://zs.91.com/");
		if (HttpNetUtil.httpRequest(str, "gb2312", localHashMap).contains(
				"发送短信成功"))
			return 1;
		return 0;
	}

	private static int getAlibabaVerifyCode(String paramString) {
		if (HttpNetUtil
				.httpRequest(
						"http://china.alibaba.com/member/sendIdentityCodeByMobile.htm?callback=jQuery1720932343235768515488058_13433069343469478&mobile="
								+ paramString, "gbk")
				.contains("success\":true"))
			return 1;
		return 0;
	}

	private static int getDouganVerifyCode(String paramString) {
		String str = HttpNetUtil
				.httpRequest(
						"http://wap.m-zone.cn/register.php?forward=&step=2&_hexie=23c15d87&getcode.x=60&getcode.y=15&code=&agreergpermit=on&phone="
								+ paramString, "UTF-8");
		//System.out.println(str);
		return 1;
	}

	private static int getFeixinVerifyCode(String paramString) {
		if ("0".equals(HttpNetUtil.httpPostRequest(
				"https://feixin.10086.cn/account/RegisterLv3smsCodeAjax",
				"utf-8", "mobileno=" + paramString)))
			return 1;
		return 0;
	}

	private static int getHimarket1VerifyCode(String paramString) {
		HashMap localHashMap = new HashMap();
		localHashMap.put("Referer", "http://apk.hiapk.com/himarket");
		if (HttpNetUtil
				.httpPostRequest(
						"http://apk.hiapk.com/HiMarketSoft/MobileHiMarket.aspx?action=sendMessage",
						"utf-8",
						"srcCode=80002&clientType=81002&phoneNo=" + paramString,
						localHashMap).contains("发送成功"))
			return 1;
		return 0;
	}

	private static int getHimarket2VerifyCode(String paramString) {
		HashMap localHashMap = new HashMap();
		localHashMap.put("Referer", "http://apk.hiapk.com/himarket");
		if (HttpNetUtil
				.httpPostRequest(
						"http://apk.hiapk.com/HiMarketSoft/PadHiMarket.aspx?action=sendMessage",
						"utf-8",
						"srcCode=80002&clientType=81001&phoneNo=" + paramString,
						localHashMap).contains("发送成功"))
			return 1;
		return 0;
	}

	private static int getKCVerifyCode(String paramString) {
		HttpNetUtil
				.httpRequest(
						"http://www.keepc.com/free/registerForMobileForCode.act?smSecurityCode=&mobileNo="
								+ paramString, "UTF-8");
		return 1;
	}

	private static int getLineKongVerifyCode(String paramString) {
		String str = HttpNetUtil.httpRequest(
				"http://passport.linekong.com/phoneRegister.do?method=sendMessage&phoneNum="
						+ paramString, "UTF-8");
		//System.out.println(str);
		return 1;
	}

	private static int getMail163VerifyCode(String paramString) {
		String str = HttpNetUtil
				.httpPostRequest(
						"http://m.mail.163.com/auth/reg.s?regtype=mobile",
						"utf-8",
						"method=registerMobile&mobile_num="
								+ paramString
								+ "&password=xx20461456&password2=xx20461456&action=%25E6%258F%2590%25E4%25BA%25A4%25E6%25B3%25A8%25E5%2586%258C%25E4%25BF%25A1%25E6%2581%25AF");
		//System.out.println(str);
		return 1;
	}

	private static int getMayiVerifyCode(String paramString) {
		if (HttpNetUtil.httpRequest(
				"http://www.mumayi.com/dl/send.php?callback=jsonp1343784635918&aid=1&xphone="
						+ paramString, "gbk").contains("true"))
			return 1;
		return 0;
	}

	private static int getQcrVerifyCode(String paramString) {
		HttpNetUtil.httpRequest("http://www.qcr.cc//sms/fs.asp?menu=1&UserMp="
				+ paramString, "UTF-8");
		return 0;
	}

	private static int getWanmeiVerifyCode(String paramString) {
		if ("200".equals(HttpNetUtil.httpPostRequest(
				"http://passport.wanmei.com/NoteAction.do", "gb2312",
				"method=sendCode&mobile=" + paramString)))
			return 1;
		return 0;
	}

	private static int getWeiboVerifyCode(String paramString) {
		String str = "http://weibo.com/signup/aj_full_mobile.php?mobilenum="
				+ paramString
				+ "&sinaId=81af5f17fda4861d3057a81d5f224c3e&rnd=0.5685412209550028";
		HashMap localHashMap = new HashMap();
		localHashMap.put("Referer",
				"http://weibo.com/signup/mobile.php?lang=zh-cn");
		if (HttpNetUtil.httpRequest(str, "UTF-8", localHashMap).contains(
				"A00006"))
			return 1;
		return 0;
	}

	private static int getWoNiuVerifyCode(String paramString) {
		HttpNetUtil
				.httpRequest(
						"http://gwpassport.woniu.com/v2/sendsms?jsoncallback=jQuery17109069836230964171_1347430548505&_=1347430564954&mobile="
								+ paramString, "UTF-8");
		return 1;
	}

	private static String parseString(String paramString1, String paramString2) {
		Matcher localMatcher = null;
		boolean bool = false;
		String str = "";
		try {
			localMatcher = Pattern.compile(paramString1).matcher(
					paramString2);
			bool = localMatcher.find();
			
			for (int i = 1;; i++) {
				if (i > localMatcher.groupCount())
					return str;
				if (!bool)
					continue;
				str = localMatcher.group(i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static int sendVerifyCode(String paramString, int paramInt) {
		switch (paramInt) {

		case 2:
		case 6:
		case 16:
		case 20:
		case 37:
			return getAlibabaVerifyCode(paramString);
		case 25:
		case 29:
			return getWanmeiVerifyCode(paramString);
		case 12:
		case 18:
		case 31:
			return getFeixinVerifyCode(paramString);
		case 14:
		case 17:
		case 36:
			return getWeiboVerifyCode(paramString);
		case 5:
		case 21:
		case 34:
			return get136EVerifyCode(paramString);
		case 7:
		case 19:
		case 24:
			return get163VerifyCode(paramString);
		case 8:
		case 13:
			return getHimarket2VerifyCode(paramString);
		case 10:
		case 23:
			return getHimarket1VerifyCode(paramString);
		case 9:
		case 22:
			return get91VerifyCode(paramString);
		case 26:
		case 27:
		case 38:
			return getQcrVerifyCode(paramString);
		case 1:
		case 30:
			return getKCVerifyCode(paramString);
		case 3:
		case 35:
			return getLineKongVerifyCode(paramString);
		case 33:
			return getWoNiuVerifyCode(paramString);
		case 15:
		case 32:
			return getMail163VerifyCode(paramString);
		case 4:
		case 11:
		case 28:
			return getDouganVerifyCode(paramString);
		default:
			return getLineKongVerifyCode(paramString);
		}

	}
}
