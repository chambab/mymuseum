package com.util;

/**
 * �� Ŭ������ <strong>���� ��ƿ��Ƽ Method</strong>�� �����ϴ� Ŭ�����̴�.
 * <BR>
 * @author 	
 * @version	1.0
 * @since 2003.10.23
 */
	
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;


import javax.servlet.http.HttpServletResponse;


public class CommonUtil {
		
	   /** 
		* String Ŭ������ ���� �����ϴ����� üũ 
		* @param  String Empty���� üũ�� String
		* @return boolean(true : null �Ǵ� �� ��Ʈ����ü, false : true �̿��� ���)
		*/
	   public static boolean isEmptyString(String arg1) {
		  if(arg1 == null || arg1.length() == 0) return true;
		  else return false;
	   }

	   /** 
		* String�� ���� null�ΰ�� Empty String�� ���� 
		* @param  String Source String
		* @return arg1�� null�� ��� Empty String�� ����
		*/
	   public static String getNotNullString(String arg1) {
		  if(arg1 == null) return "";
		  else return arg1;
	   }

	   /** Object�� Length�� Return�Ѵ�.
		*  String �Ǵ� StringBuffer�� ��� : length() ����
		*  List �Ǵ� Map�� ��� size() ����
		*  Array�� ��� length ����
		*  null �ΰ�� 0�� ����
		*  �� �̿��� ��� 1�� ����
		*/
	   public static int getLength(Object obj) throws Exception {
		  if(obj == null) {
			 return 0;
		  }
		  else if(obj instanceof String || obj instanceof StringBuffer) {
			 return ((String)obj).length();
		  }
		  else if(obj instanceof List || obj instanceof Map) {
			 return ((List)obj).size();
		  }
		  else if(obj.getClass().isArray()) {
			 return Array.getLength(obj);
		  }
		  return 1;
	   }

	   /**
		* ���糯¥�� ������ ���信 �°� String ���·� �������ش�
		* @param argFormat java.lang.String
		* @return String
		* @exception TException
		* Example
		*      Format Pattern                         Result
		*     --------------                         -------
		*     "yyyy.MM.dd G 'at' HH:mm:ss z"    ->>  1996.07.10 AD at 15:08:56 PDT
		*     "EEE, MMM d, ''yy"                ->>  Wed, July 10, '96
		*     "h:mm a"                          ->>  12:08 PM
		*     "hh 'o''clock' a, zzzz"           ->>  12 o'clock PM, Pacific Daylight Time
		*     "K:mm a, z"                       ->>  0:00 PM, PST
		*     "yyyyy.MMMMM.dd GGG HH:mm aaa"    ->>  1996.July.10 AD 12:08 PM
		*/
	   public static String getDateFormat(String argFormat) throws Exception {
		  if(argFormat == null || argFormat.length() == 0) throw new Exception("��¥ ���伳�� ����");

		  String returnStr = null;
		  DateFormat df = new SimpleDateFormat(argFormat);
		  Calendar cal = Calendar.getInstance();
		  returnStr = df.format(cal.getTime());

		  return returnStr;
	   }
	   
	   /**
	    * long Ÿ������ ��ȯ�Ǵ� ���� 1970��1��1��00:00:00�� ���������ϴ� millisecond���� ��
	    *
	    * @return
	    */
	   public static long getMilliseconds() {
//		millisecond �� ��ȯ 
		Calendar c1 = Calendar.getInstance(); 
 		return c1.getTime().getTime(); 
	  }

	   /** ���糯¥�� yyyyMMdd Format���� ���� */
	   public static String getDate() throws Exception {
		  return getDateFormat("yyyyMMdd");
	   }

	   /** ���糯¥�� yyyy-MM-dd Format���� ���� */
	   public static String getDashedDate() throws Exception {
		  return getDateFormat("yyyy-MM-dd");
	   }

	   /** yyyyMMdd ������ String�� yyyy-MM-dd Format���� ���� */
	   public static String getDashedDate(String strDate) {
		  if(strDate == null || strDate.length() < 8) return strDate;
		  return strDate.substring(0,4)+"-"+strDate.substring(4,6)+"-"+strDate.substring(6,8);
	   }

		/** ���糯¥�ð��� yyyyMMddhhmmss Format���� ���� */
	   public static String getDateTime() throws Exception {
		  return getDateFormat("yyyyMMddHHmmss");
	   }
	   
		/** ������� �ֱٺб���� MM Format���� ���� */
		public static String getQuarterMonth(String strMonth) throws Exception {
			String rtnMonth  = new String();
			if(strMonth.equals("12")||strMonth.equals("01")||strMonth.equals("02")) rtnMonth="12";
			if(strMonth.equals("03")||strMonth.equals("04")||strMonth.equals("05")) rtnMonth="03";
			if(strMonth.equals("06")||strMonth.equals("07")||strMonth.equals("08")) rtnMonth="06";
			if(strMonth.equals("09")||strMonth.equals("10")||strMonth.equals("11")) rtnMonth="09";
			return rtnMonth;
		}
	   
	   /** ���糯¥�ð��� milliseconds �� ���� */
	   
	   /** '-'�� ���е� ��ȭ��ȣ�� '-'�� ������ 4���� String Array�� �����Ѵ�.
		*  Token�� ������ ��� �������� �����Ͽ� Array�� ä���.
		*/
	   public static String[] split4PhoneNumber(String strphone) {
		  int fieldlen = 4;
		  String[] rtnStr = new String[fieldlen];
		  if(strphone != null) {
			 StringTokenizer st = new StringTokenizer(strphone, "-");
			 int count = fieldlen-st.countTokens();
			 while(st.hasMoreTokens()) {
				rtnStr[count] = st.nextToken();
				count++;
			 }
		  }
		  return rtnStr;
	   }

	   /** StringBuffer�� �ִ� ���� New Line�� �ش�Ǵ� ���� �����
		* @param  java.lang.StringBuffer
		* @return void
		* @exception Exception
		*/
	   public static void removeNewLine(StringBuffer sb) throws Exception {
		  if(sb == null) throw new Exception("StringBuffer �Ķ���� ����(Null)");

		  char ch;
		  int i, sblen = sb.length();
		  for(i=0;i<sblen;i++) {
			 if((ch = sb.charAt(i)) == '\n' || ch == '\r') {
				sb.deleteCharAt(i);
				i--;
				sblen--;
			 }
			 else if(ch == '"') {
				sb.setCharAt(i, '\'');
			 }
		  }
	   }
   
	   /**
		* String�� �ִ� ���� New Line�� �ش�Ǵ� ���� �����
		* @param  String
		* @return String
		*/
	   public static String removeNewLine(String s)
	   {
		  StringBuffer strBf = new StringBuffer(s);
		  String s1  = new String();
		  try
		  {
			   removeNewLine(strBf);
			  s1 = strBf.toString();
		  }catch(Exception e)
		  {
			  System.out.println("String Conversion Error");
		  }
		  return s1;
	   }

	   /**
		* String�� �ִ� ���� (',", ,-,_)�� �ش�Ǵ� ���� �����
		* @param  String
		* @return String
		*/
	   public static String removeSearchSpacialChars(String s)
	   {
		  if(s != null) {
		   StringBuffer sb = new StringBuffer(s);
			 char ch;
			 int i, sblen = sb.length();
			 for(i=0;i<sblen;i++) {
				if((ch = sb.charAt(i)) == '\'' || 
					ch == '-' ||
					ch == '"' ||
					ch == ' ' ||
					ch == '_') {
				   sb.deleteCharAt(i);
				   i--;
				   sblen--;
				}
			 }
		   return sb.toString();
		}
		return "";
	   }


	   /** 
		* StringBuffer�� �ִ� ���� New Line�� �ش�Ǵ� �� <BR>�� ����
		* @param java.lang.StringBuffer
		* @return void
		* @exception Exception
		*/
	   public static void convertNewLine(StringBuffer sb) throws Exception {
		  if(sb == null) throw new Exception("StringBuffer �Ķ���� ����(Null)");

		  char ch;
		  int i, sblen = sb.length();
		  for(i=0;i<sblen;i++) {
			 if((ch = sb.charAt(i)) == '\n') {
				sb.deleteCharAt(i);
				sb.insert(i, "<br>");
				i+=3;
				sblen+=3;
			 }
			 else if(ch == '\r') {
				sb.deleteCharAt(i);
				i--;
				sblen--;
			 }
			 else if(ch == '"') {
				sb.setCharAt(i, '\'');
			 }
		  }
	   }

	   /** 
		* str�� �ִ� ���� Double Quote(") �տ� Escape����(\)�� �߰�
		* @param  String
		* @return String
		*/
	   public static String convertDoubleQt(String str) {
		  if(str == null)
			 return str;

		  char ch;
		  StringBuffer stb = new StringBuffer();
		  stb.append(str);

		  int i, sblen = stb.length();
		  for(i=0;i<sblen;i++) {
			 if((ch = stb.charAt(i)) == '\"')
			 {
				stb.insert(i, "\\");
				i+=1;
				sblen+=1;
			 }
		  }
		  return stb.toString();
	   }

	   /** 
		* str�� �ִ� ���� Single Quote(') �տ� Escape����(\)�� �߰�
		* @param  String
		* @return String
		*/
	   public static String convertSingleQt(String str) {
		  if(str == null)
			 return str;

		  char ch;
		  StringBuffer stb = new StringBuffer();
		  stb.append(str);

		  int i, sblen = stb.length();
		  for(i=0;i<sblen;i++) {
			 if((ch = stb.charAt(i)) == '\'')
			 {
				stb.insert(i, "\'");
				i+=1;
				sblen+=1;
			 }
		  }
		  return stb.toString();
	   }
	   /** 
		* Source String�� ������ ���̸� �����Ҷ����� Ư�����ڷ� ä���� ����
		* @param srcString  String
		* @param dstLength  int
		* @param appendchar char
		* @return String
		*/
	   public static String Rpad(String srcString, int dstLength, char appendchar) {
		  int srcLength = (srcString == null)?0:srcString.length();
		  int appendLength = dstLength - srcLength;
		  if(appendLength > 0) {
			 int i;
			 char [] newchar = new char[dstLength];
			 srcString.getChars(0, srcLength, newchar, 0);
			 for(i=0;i<appendLength;i++) {
				newchar[srcLength+i] = appendchar;
			 }
			 return new String(newchar);
		  }
		  else return srcString;
	   }

	   /**
	   * \n, \r ���� <br>�ױ׷� �����Ų��.<br>
	   * @param comment  �����ų ���ڿ�
	   * @return String  ����� ���ڿ�
	   */
	   public static String toBr(String comment) {
		  if( comment==null || comment.equals("") || comment.equals(null) ) {
			 return "";
		  } else {
			 int length = comment.length();
			 StringBuffer buffer = new StringBuffer();
			 for(int i=0;i<length;++i) {
				String comp = comment.substring(i,i+1);
				if ("\r".compareTo(comp) == 0) {
				   comp = comment.substring(++i,i+1);
				   if ("\n".compareTo(comp) == 0) buffer.append("<br>\r");
				   else buffer.append("\r");
				}
				buffer.append(comp);
			 }
			 return buffer.toString();
		  }
	   }





	   /** ���ڿ����� script  �ױ׸� �����Ͽ� ����  */
	   public static String convertContents(String s)
	   {
		StringBuffer strBf = new StringBuffer(s);
		String s1  = new String();
		try
		{
			convertNewLine(strBf);
			s1 = strBf.toString();
			String tmp = s1.toLowerCase();
			if(tmp.indexOf("<script") >= 0) 	
			   s1 = replaceIgnoreCase(s1, "<script", "&lt;script");
			if(tmp.indexOf("</script") >= 0)  
			   s1 = replaceIgnoreCase(s1, "</script", "&lt;/script");
			if(tmp.indexOf("< /script") >= 0) 
			   s1 = replaceIgnoreCase(s1, "</ script", "&lt;/script");
		}catch(Exception e)
		{
			System.out.println("String Conversion Error");
		}
		return s1;
	   }
	   /** textarea�� ǥ���ϱ� ���� ������ �����Ͽ� ����  */
	   public static void convertNewLine_Textarea(StringBuffer sb) throws Exception {
			 if(sb == null) throw new Exception("StringBuffer �Ķ���� ����(Null)");

			 char ch;
			 int i, sblen = sb.length();
			 for(i=0;i<sblen;i++) {
				if((ch = sb.charAt(i)) == '\n') {
				   sb.deleteCharAt(i);
				   sb.insert(i, "\\n");
				   i+=1;
				   sblen+=1;
				}
				else if(ch == '\r') {
				   sb.deleteCharAt(i);
				   i--;
				   sblen--;
				}
				else if(ch == '"') {
				   sb.setCharAt(i, '\'');
				}
			 }
	   }
	   /** 
		* ���ڿ� Ư�� ���ڿ��� ��ġ�� �� ����  
		* @param String Source String
		* @param String ������ ���ڿ�(FROM)
		* @param String ������ ���ڿ�(TO)
		* @return String 
		*/
	   public static String replaceIgnoreCase(String source, String pattern, String replace) 
	   { 
		   int sIndex = 0;
		   int eIndex = 0;
		   String sourceTemp = null;
		   StringBuffer result = new StringBuffer();    
		   sourceTemp = source.toUpperCase();
		   while ((eIndex = sourceTemp.indexOf(pattern.toUpperCase(), sIndex)) >= 0) 
		   { 
			   result.append(source.substring(sIndex, eIndex)); 
			   result.append(replace); 
			   sIndex = eIndex + pattern.length(); 
		   } 
		   result.append(source.substring(sIndex)); 
		   return result.toString(); 
	   } 

	   /**
		* Request ��ü�� ���Ե� ��Ű�� name�� ��ġ�ϴ� �Ѱ��� String���� �������ش�
		*/
	   public static String getCookie(javax.servlet.http.HttpServletRequest request, String name) throws Exception {
		  if(request == null) throw new Exception("HttpServletRequest �Ķ���Ͱ� Null �Դϴ�");
		  if(name == null) throw new Exception("��Ű�� �Ķ���Ͱ� Null �Դϴ�");

		String value = "";
		javax.servlet.http.Cookie[] cook = request.getCookies();
   	
		if(cook != null) {
		  for(int i=0;i < cook.length; i++) { 
			 if(cook[i].getName().equals(name)) {
				value = cook[i].getValue();
				break;
			 }
		  }
		}
   		
		return value;
	   }

	/**
 	 * Request ��ü�� ���Ե� ��Ű�� name�� ��ġ�ϴ� ���� Setting �Ѵ�.
	 * @param request
	 * @param name
	 * @param value
	 * @throws Exception
	 */
//	public static void setCookie(HttpServletResponse response, String name, String value) throws Exception {
//	   	if(response == null) throw new Exception("HttpServletResponse �Ķ���Ͱ� Null �Դϴ�");
//	   	if(name == null) throw new Exception("��Ű�� �Ķ���Ͱ� Null �Դϴ�");
//
//		Cookie cookie = new Cookie(name, value);
//		cookie.setMaxAge(-1);
//	 	response.addCookie(cookie);  	
//	}

	/**
	 * Request ��ü�� ���Ե� ��Ű�� name�� ��ġ�ϴ� ���� ���� �Ѵ�.
	 * @param request
	 * @param name
	 * @return
	 * @throws Exception
	 */
//	public static void removeCookie(HttpServletResponse response, String name) throws Exception {
//	if(response == null) throw new Exception("HttpServletResponse �Ķ���Ͱ� Null �Դϴ�");
//	if(name == null) throw new Exception("��Ű�� �Ķ���Ͱ� Null �Դϴ�");
//	Cookie cookie = new Cookie(name, "");
//	cookie.setMaxAge(0);
//	response.addCookie(cookie);
//	}
      
	   /** 
		* StringBuffer�� ������ ����(len) ������ �ٹٲ�
		* @param java.lang.StringBuffer
		* @return void
		* @exception Exception
		*/
	   public static void autoLine(StringBuffer sb, int len) throws Exception {
		  if(sb == null) throw new Exception("StringBuffer �Ķ���� ����(Null)");

		  char ch;
		  int i, sblen = sb.length();
		  for(i=0; i<sblen; i++) {
			 if((i % len) == 0) {
				sb.insert(i, "<br>");
				i+=3;
				sblen+=3;
			 }
		  }
	   }   

	   /** 
		* 2���� ArrayList�� int������ ũ�� ���� Method�� ���ؼ� ������ 
		* ���� �� �����Ͽ� �ΰ��� ArrayList�� ���Ѵ�.
		* @param ArrayList ù��° ���Ĵ�� ArrayList
		* @param ArrayList �ι�° ���Ĵ�� ArrayList
		* @param Object ArrayList�� �����ϴ� �񱳴�� class
		* @param String ũ�� ���� Method��
		* @return ���� ���ĵǿ� �Ѱ��� ������ ArrayList
		* @exception Exception
		*/
	   public static ArrayList mergeSortArrayList(ArrayList arr1, ArrayList arr2, Object objVO, String methodName) {
		  ArrayList prvlist = null;
		  if(arr1 == null || arr1.size() == 0) {
			 prvlist = arr2;
		  }
		  else if(arr2 == null || arr2.size() == 0){
			 prvlist = arr1;
		  }
		  else {
			 boolean blastloop = false;
			 int j=0, k, arr2seq;
			 int arr2size = arr2.size();
			 int arr1size = arr1.size();
			 int arr1seq = getObjSeqValue(arr1.get(0), methodName);
			 for(int i=0;i<arr2size && !blastloop;i++) {
				arr2seq = getObjSeqValue(arr2.get(i), methodName);
				while(arr2seq > arr1seq) {
				   arr2.add(i++, arr1.get(j++));
				   arr2size++;
				   if(j<arr1size) {
					  arr1seq = getObjSeqValue(arr1.get(j), methodName);
				   }
				   else {
					  blastloop = true;
					  break;
				   }
				}
			 }
			 for(k=j;k<arr1size;k++) {
				arr2.add(arr1.get(k));
			 }
			 prvlist = arr2;
		  }
		  return prvlist;
	   }
	   /**
		* Object���� ������ Method�� ȣ���Ͽ� �� ��� return�Ѵ�.
		* ��, ���� int ���� ���� �����Ѵ�.
		* @param  Object ���� ȣ���� Object
		* @param  String Object���� ȣ���� Method��
		* @return ȣ��� ��� �� int
		*/
	   public static int getObjSeqValue(Object obj, String methodName) {
		  int iRtn = -1;
		  try {		  	
			 iRtn = ((Integer)(obj.getClass().getMethod(methodName, new Class[]{}).invoke(obj, new Object[]{}))).intValue();
		  }
		  catch(Exception e) {}
		  return iRtn;
	   }
	   
	public static String itemReplace(String s, String search, String replace)  
	{
            
		StringBuffer s2 = new StringBuffer();     
		int i = 0;     
		int j = 0;
		int sl = search.length();
 
		while (j > -1) {
			j = s.indexOf(search,i);         
			if (j > -1)  {
				s2.append (s.substring(i,j)+replace);              
				i = j + sl;
			}         
		}     
		s2.append(s.substring(i,s.length()));
		return s2.toString();
	}
	
	/**
	   * parameter�� �Ѿ�� string������ null�̸�
	   * ����� string���� �ѱ��.
	   <br> Usage : UtilFormat.replaceNull(value);
   
		 @param value nulltrim �� �ϰ��� �ϴ� value
		 @return String nulltrim �� value
	   */     
	public static String replaceNull(String value) 
	{
		if(value == null || value.equals("")) 
			return (new String(""));
		else return(value.trim());
	}

  /**
   * parameter�� �Ѿ�� long������ null�̸�
   * ����� string���� �ѱ��.
	 @param value nulltrim �� �ϰ��� �ϴ� value
	 @return long nulltrim �� value
   */        
		public static long replaceNull(long value) 
		{
			Long tmpLong = new Long(value);
			if(tmpLong == null) 
				return (0);
			else return(value);
		}
   
	  /**
   * parameter�� �Ѿ�� long������ null�̸�
   * ����� string���� �ѱ��.
	 @param value nulltrim �� �ϰ��� �ϴ� value
	 @return long nulltrim �� value
   */        
		public static int replaceNull(int value) 
		{
			Integer tmpInt = new Integer(value);
			if(tmpInt == null) 
				return (0);
			else return(value);
		}
   
	  /**
   * parameter�� �Ѿ�� double������ null�̸�
   * ����� string���� �ѱ��.
	 @param value nulltrim �� �ϰ��� �ϴ� value
	 @return double nulltrim �� value
   */   
	public static double replaceNull(double value) 
	{
		Double tmpDouble = new Double(value);
		if(tmpDouble == null) 
			return (0);
		else return(value);
	}
	
	public static Vector ObjectList2Vector(Object[] obj) {
		Vector vc = new Vector();
		for(int i=0; i < obj.length; i++)
		{
			vc.addElement(obj[i]);
		} 
		return vc;
	}	
	
	/** seperator�� ������ �� �ִ� strings��
	 *  String Array�� return �Ѵ�.
	 */
	public static String[] getArrayFromString(String strings, String separator) {
	   StringTokenizer arrayString = new StringTokenizer(strings, separator);
	   String[] rtnStr = new String[arrayString.countTokens()];
	   if(strings != null) {
		  int count = 0;
		  while(arrayString.hasMoreTokens()) {
			 rtnStr[count] = arrayString.nextToken();
			 count++;
		  }
	   }
	   return rtnStr;
	}
	
	/**
	 * 
	 * @param hm : String ���� ���� key�� ������ ���� 
	 * @param deliminator1 key=value ���� "=" �� ���� deliminator
	 * @param deliminator2 key=value&key=value ���� "&" �� ���� deliminator
	 * @return
	 */
	public static String mapToString(HashMap hm, String deliminator1, String deliminator2) {
		StringBuffer strBuf = new StringBuffer();
		// �˻� ���� ����
		for (Iterator i=hm.entrySet().iterator(); i.hasNext(); ) {
			if(strBuf.length()>0) strBuf.append(deliminator2);
			Map.Entry e = (Map.Entry) i.next();
			if((String) e.getKey() != null && e.getValue() != null) {
				strBuf.append(e.getKey() + deliminator1 + e.getValue());
			}
		}
		return strBuf.toString();
	}
	

	/** 
	 * Hash Tag 추출용
	 * @param strings
	 * @param separator
	 * @return
	 */
	public static String[] getHashTag(String strings, String separator) {
		String[] rtnStr =  null;
		ArrayList array = new ArrayList();
		String remainStr = null;
	
		int lengthCheck = 0;
		
		remainStr = strings;
		while(remainStr.indexOf(separator) >= 0) {
			int shapIndex = remainStr.indexOf(separator);
			remainStr = remainStr.substring(shapIndex);
			
			lengthCheck = remainStr.indexOf(" ");
			if(lengthCheck < 0) {
				array.add(remainStr.substring(0));
				remainStr = "";
			} else {
				array.add(remainStr.substring(0, remainStr.indexOf(" ")));
				remainStr = remainStr.substring(remainStr.indexOf(" "));
			}
			
		}
		rtnStr = new String[array.size()];
		rtnStr = (String[]) array.toArray(rtnStr);
		
		return rtnStr;
	}    
}

