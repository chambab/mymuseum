package com.util;

public class Encoder
{
        public static String toKor(String str)
        {
                if (str == null) return "";
                try
                {
                        str = new String(str.getBytes("8859_1"), "euc-kr");
                        //str = str;
                }
                catch(Exception ex)
                {
                    System.out.println("Exception : " + ex);
                }
                return str;
        }
        public static String toUni(String str)
        {
                if (str == null) return "";
                try
                {
                        str = new String(str.getBytes("euc-kr"), "8859_1");
                }
                catch(Exception ex)
                {
                    System.out.println("Exception : " + ex);
                }
                return str;
        }
        public static String conv(String str)
        {
                String strCapital = str;
                int len = strCapital.length();
                int remain = len % 3;
                String result = "";
                int j=0;
                Boolean bFirst = Boolean.TRUE;
                if(strCapital.length() <= 3)
                {
                        return strCapital;
                }
                for(int i=0; i < strCapital.length(); i++)
                {
                        j++;
                        if(j == remain && bFirst == Boolean.TRUE)
                        {
                                result += (strCapital.charAt(i) + ",");
                                j = 0;
                                bFirst = Boolean.FALSE;
                        }
                        else if((j%3)==0)
                        {
                                if(i != (strCapital.length() - 1))
                                {
                                        result += (strCapital.charAt(i) + ",");
                                        j = 0;
                                }
                                else
                                {
                                        result += (strCapital.charAt(i));
                                }
                        }
                        else
                                result += strCapital.charAt(i);
                }
                return result;
        }
        public static String toDate(String str, String separator)
        {
                String szDate = null;
                String year = null;
                String month = null;
                String day = null;

                if(str.length() >= 4)
                        year = str.substring(0,4);
                if(str.length() >= 6)
                        month = str.substring(4,6);

                if(str.length() >= 8)
                        day = str.substring(6,8);
                else
                        day = str.substring(6);

                szDate = year + separator + month + separator + day;
                return szDate;
        }
        public static String toEngDate(String str, String separator)
        {
                String szDate = null;
                String year = null;
                String month = null;
                String day = null;

                if(str.length() >= 4)
                        year = str.substring(0,4);
                if(str.length() >= 6)
                        month = str.substring(4,6);

                if(str.length() >= 8)
                        day = str.substring(6,8);
                else
                        day = str.substring(6);

                szDate = year + separator + Encoder.toEng(month) + separator + day;
                return szDate;
        }
	//
	//	�Էµ� ��Ʈ���� ���ڸ� �����(���̸�)�� �����Ͽ� Return �Ѵ�
	public static String toEng(String strMonth)
	{
		String strCal[] = {"January", "February", "March", "April", "May",
                                     "June", "July", "August", "September", "October",
                                     "November", "December"}; 
		int nMonth = Integer.parseInt(strMonth);
		return strCal[nMonth];
	}
}
