/*
 * Created on 2005. 6. 8.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.util;

import java.util.StringTokenizer;


/**
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SecurityUtil {
	// SQL-Injection, XSS �� �����Ѵ�
	public static String stringCheck(String arg, boolean tagable){
		String rStr	= arg;
		if(arg == null || "".equals(arg)){
			return "";
		}

		// SQL-Injection Check
		// ���� �����ص� Sql - Injection �� ���Ǵ� ���ڿ��� �޾ƿ´�
		StringTokenizer st	= new StringTokenizer("',-,union,select",",");
		String imsiStr	= null;

		// ������� �ʴ� ���ڿ��� "" �� ��ȯ�Ѵ�.
		while(st.hasMoreTokens()){
			imsiStr	= st.nextToken();
			rStr	= CommonUtil.itemReplace(rStr,imsiStr,"");
		}

		// XSS ����
		// ��� "<,>"�� ��ȯ�Ѵ�.
		rStr	= CommonUtil.itemReplace(rStr,"<","&lt;");
		rStr	= CommonUtil.itemReplace(rStr,">","&gt;");

		// �±� ��뿩�� Ȯ��
		if(tagable){
			rStr = stringTagAblable(rStr);
		}

		return rStr;
	}

	public static String stringTagAblable(String arg){
		String rStr = arg;
		if(arg == null || "".equals(arg)){
			return "";
		}


		String closeTag = null;
		String tagName	= null;
		// TODO
		//	����
		//StringTokenizer tList   = new StringTokenizer((String)ConfigManager.getProperty("security.tag.ablelist"),",");
		StringTokenizer tList   = null;
		// ��밡���� �±׸� ����Ѵ�.
		while(tList.hasMoreTokens()){
			tagName = tList.nextToken();
			rStr    = CommonUtil.itemReplace(rStr,"&lt;"+tagName+"&gt;","<"+tagName+">");
			closeTag    = "&lt;/" + tagName + "&gt;";
			if(rStr.indexOf(closeTag) > -1)
				rStr    = CommonUtil.itemReplace(rStr,closeTag,"</"+tagName+">");
		}
		return rStr;
	}
	

}

