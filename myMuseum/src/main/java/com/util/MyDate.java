package com.util;
import java.util.*;

public class MyDate
{
	Calendar today;
	private String szDay;
    	private String szMonth;
    	private String szYear;
	private int nDay;
	private int nMonth;
	private int nYear;

    	private int nmaxDayofMonth;
    	private int nminWeekofMonth;
	private String m_today;

        public MyDate()
        {
        	today = Calendar.getInstance();
		//
		//	������ ��¥�� ���Ѵ�
		m_today = Integer.toString(today.get(Calendar.YEAR)) + 
                	  toStr(today.get(Calendar.MONTH) + 1) + 
                	  toStr(today.get(Calendar.DAY_OF_MONTH));
        }
        public void setData(String ymd)
        {
		this.szYear  = ymd.substring(0,4);
		this.szMonth = ymd.substring(4,6);
		this.szDay   = ymd.substring(6,8);

		this.nDay    = Integer.parseInt(szDay);
		this.nMonth  = Integer.parseInt(szMonth);
		this.nYear   = Integer.parseInt(szYear);

                setMaxMin();
	}
        private void setMaxMin()
        {
                today.set(nYear, nMonth - 1, nDay);
                nmaxDayofMonth = today.getActualMaximum(Calendar.DAY_OF_MONTH);
                today.set(Integer.parseInt(szYear), Integer.parseInt(szMonth), 1);
                nminWeekofMonth = today.get(Calendar.DAY_OF_WEEK);
                today.set(Integer.parseInt(szYear), Integer.parseInt(szMonth), Integer.parseInt(szDay));
        }
	private String toStr(int nValue)
	{
		String newValue = null;
		if(nValue < 10)
			newValue = "0" + Integer.toString(nValue);	
		else
			newValue = Integer.toString(nValue);
		return newValue;
	}
        public int getMaxDayofMonth() { return this.nmaxDayofMonth; }
        public int getMinWeekofMonth() { return this.nminWeekofMonth; }

        //
        //      Today
        //public String getDay() { return this.szDay; }
        //public String getMonth() { return this.szMonth; }
        //public String getYear() { return this.szYear; }
        public String getToday(){ return m_today; }
	//
	//	ymd ��¥�� ������
	public String getNextDay(String ymd)
	{
		setData(ymd);
		String szNextDay = null;

		int nNextYear = this.nYear;
		int nNextMonth = this.nMonth;
		int nNextDay = this.nDay + 1;
		if(nNextDay > this.nmaxDayofMonth)
		{
			nNextDay = 1;
			nNextMonth = nNextMonth + 1;
			if(nNextMonth > 12)
			{
				nNextMonth = 1;
				nNextYear = nNextYear + 1;
			}
		}
		szNextDay = Integer.toString(nNextYear) + toStr(nNextMonth) + toStr(nNextDay);		
		return szNextDay;	
	}
	//
	//	���� ��¥�� ������
	public String getNextDay()
	{
		String m_nextday = this.getNextDay(this.getToday());
		return m_nextday;
	}
	//
	//	���� ��¥�� ����
	public String getPreDay()
	{
		String m_preday = this.getPreDay(this.getToday());
		return m_preday;
	}
	//
	//	ymd ���� ����
	public String getPreDay(String ymd)
	{
		setData(ymd);
		String szPreDay = null;
		int nPreYear = this.nYear;
		int nPreMonth = this.nMonth;
		int nPreDay = this.nDay - 1;
		if(nPreDay < 1)
		{
			nPreMonth = nPreMonth - 1;
			if(nPreMonth < 1)
			{
				nPreYear = nPreYear - 1;
				nPreMonth = 12;
			}
			today.set(nPreYear, nPreMonth - 1, 1);
			nPreDay = today.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		szPreDay = Integer.toString(nPreYear) + toStr(nPreMonth) + toStr(nPreDay);
		today.set(nYear, nMonth - 1, nDay);	
		return szPreDay;
	}
}
