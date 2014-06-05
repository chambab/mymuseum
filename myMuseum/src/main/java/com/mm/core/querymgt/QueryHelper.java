package com.mm.core.querymgt;
import java.io.CharArrayReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.BatchUpdateException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.sql.CLOB;


public class QueryHelper {
	public static ResultSet executeQuery (Connection con, String sql) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
		} catch(Exception e) {
			System.err.println(e.toString());
		}
		return rs;
	}
	public static String selectCtlCggCode(Connection con, String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		String cgg_code = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
				cgg_code = rs.getString("ctl_cgg_code");
			
			rs.close();
			stmt.close();			
		} catch(Exception e) {
			System.err.println(e.toString());
		}

		return cgg_code;
	}	
	public static int executeUpdate (Connection con, String sql) throws SQLException{
		Statement stmt = null;
		int result = 0;

		try {
			stmt = con.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println(e.toString());
			throw e;
		}
		return result;
	}
	
	public static ResultSet executeQuery (Connection con, String sql, String[] params) {
		PreparedStatement pstmt = null;		
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}			
			rs = pstmt.executeQuery();				
		} catch(SQLException e) {
			System.err.println("SELECT ERROR1 : " + e.toString());
		}
		return rs;
	}
	public static ArrayList executeQueryA(Connection con, String sql, String[] params) {
		PreparedStatement pstmt = null;			
		ResultSet rs = null;
		String columnName = null;
		int columnCount = 0;
		HashMap hm = null;
		ArrayList ar = new ArrayList();
		//int i=0;
		try {
			pstmt = con.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}			
			rs = pstmt.executeQuery();				
			
			while(rs.next())
			{
				//columnName = rs.getMetaData().getColumnName(i++);
				if(hm == null) hm = new HashMap();
				//hm.put(columnName, rs.getString(columnName));
				
				columnCount = rs.getMetaData().getColumnCount();
				for(int i=0; i < columnCount ; i++)
				{
					columnName = rs.getMetaData().getColumnName(i+1);					
					hm.put(columnName, rs.getString(columnName));
				}
				ar.add(hm);
			}
			rs.close();
			pstmt.close();
		} catch(SQLException e) {
			System.err.println("SELECT ERROR2 : " + e.toString());
		}
		return ar;
	}	
	/**
	 * Connection, Pstmt
	 * @param con
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet executeQuery (String[] params, PreparedStatement pstmt) {		
		ResultSet rs = null;

		try {

			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}			
			rs = pstmt.executeQuery();			
		} catch(SQLException e) {
			System.err.println("SELECT ERROR3 : " + e.toString());
		}
		return rs;
	}
	
	
	public static int executeUpdate (Connection con, String sql, String[] params) throws SQLException{
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = con.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				pstmt.setString(i + 1, params[i]);
			}
			
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			System.err.println(e.toString());
			throw e;
		}
		return result;
	}	
	public static String[] getParam(ResultSet rset, String[] params)
	{
		String[] szParam = new String[params.length];

		for(int i=0; i < params.length; i++)
		{			
			try {
				szParam[i] = rset.getString(params[i]);
			}catch(SQLException e) {
				szParam[i] = "0";
			}
		}

		return szParam;
	}	
	public static int[] executeBatchCkUpdate(Connection con, String sqlMem, String sqlIf, ResultSet paramSet)
	{
		String[] params = { "VOLT_ID","VOLT_NM","VOLT_SID","SID_SNO","TELNO",
				"HPNO","WK_TELNO","VOLN_ACTV_PD","VOLN_ACTV_MINT",
				"MEM_STATE","CTL_CGG_CODE","CT_ORG_CODE",
				"FRST_REG_TS","FRST_REGR_ID","LAST_MOD_TS","LAST_CORT_ID"};
		String[] memLogonParams = {"VOLT_ID","REG_PWD","MEM_AUTH","VOLT_NM","EMAIL","MEM_SE","CTL_CGG_CODE","CT_ORG_CODE",
	                   "FRST_REG_TS","FRST_REGT_ID","LAST_MOD_TS","LAST_CORT_ID"};


		PreparedStatement pstmtMem = null;
		PreparedStatement pstmtIf = null;
		int result[] = null;
		int resultIf[] = null;
		int[] updateCnts = null;
		int   updateCnt = 0;
		int result_size = 0;
		int term = 0;
		int fieldSize = 0;
		int fetchSize = 0;
		try {			
			pstmtMem = con.prepareStatement(sqlMem);
			pstmtIf = con.prepareStatement(sqlIf);

			while(paramSet.next())
			{
//				fetchSize++;
//				term++;
				fieldSize = paramSet.getMetaData().getColumnCount();
				
				String[] param = QueryHelper.getParam(paramSet, memLogonParams);
				String[] paramIf = QueryHelper.getParam(paramSet, params);
				for (int i = 0; i < param.length; i++) {
	
					pstmtMem.setString(i + 1, param[i]);
				}
				for(int i=0; i < paramIf.length; i++)
				{
					pstmtIf.setString(i + 1, paramIf[i]);
				}
				//
				//	Batch
				pstmtMem.addBatch();	
				pstmtIf.addBatch();
//				if(term > 4999)
//				{
//					result = pstmt.executeBatch();							
//					pstmt.clearBatch();					
//					result_size = result_size + result.length;
//					result = null;
//					term = 0;
//				}
			}		
//			System.out.println("FETCH SIZE [" + fetchSize + "] : ");
			//
			//	Batch
			

			result = pstmtMem.executeBatch();	

			resultIf = pstmtIf.executeBatch();	
			pstmtMem.clearBatch();
			pstmtIf.clearBatch();
			
			result_size = result_size + result.length;
			result = new int[result_size];
			pstmtMem.close();
			pstmtIf.close();
		} catch(BatchUpdateException e) {
			System.err.println(e.toString());		
			updateCnts = e.getUpdateCounts();
		
				
			
		} catch(SQLException e)
		{

			System.err.println(e.toString());
		}
		return result;	
	}
	/**
	 * Batch Update
	 * @param con
	 * @param sql
	 * @param paramSet
	 * @return
	 * @throws SQLException 
	 */
	public static int[] executeBatchUpdate (Connection con, String sql, ResultSet paramSet) throws SQLException {
		PreparedStatement pstmt = null;
		int result[] = null;
		int result_size = 0;
		int term = 0;
		int fieldSize = 0;
		int fetchSize = 0;
		try {			
			pstmt = con.prepareStatement(sql);

			while(paramSet.next())
			{
				fetchSize++;
				term++;
				fieldSize = paramSet.getMetaData().getColumnCount();
				
				for (int i = 0; i < fieldSize; i++) {
					//System.out.println("VOLT_ID : " + paramSet.getString(i+1));
					pstmt.setString(i + 1, paramSet.getString(i+1));
				}
				//
				//	Batch
				pstmt.addBatch();				
				if(term > 9999)
				{
					result = pstmt.executeBatch();							
					pstmt.clearBatch();					
					result_size = result_size + result.length;
					result = null;
					term = 0;
				}
			}		
			System.out.println("FETCH SIZE [" + fetchSize + "] : ");
			//
			//	Batch
			result = pstmt.executeBatch();	
			pstmt.clearBatch();
			result_size = result_size + result.length;
			result = new int[result_size];
			pstmt.close();
		} catch(SQLException e) {
			//System.err.println(e.toString());
			throw e;
		}
		return result;
	}	
	/**
	 * Primary Key�� Dup
	 * @param con
	 * @param sql
	 * @param paramSet
	 * @return
	 * @throws SQLException
	 */
	public static int[] executeBatchUpdateDupCk (Connection con, String sql, ResultSet paramSet, String[] dupCkField) throws SQLException {
		PreparedStatement pstmt = null;
		int result[] = null;
		int result_size = 0;
		int term = 0;
		int fieldSize = 0;
		int fetchSize = 0;
		int dupCount = 0;
		HashMap dupList = new HashMap();
		String keyValue = "";
		try {			
			pstmt = con.prepareStatement(sql);

			while(paramSet.next())
			{
				fetchSize++;
				term++;
				fieldSize = paramSet.getMetaData().getColumnCount();
				
				for (int i = 0; i < fieldSize; i++) {
					
					//
					//	Dup Check
					for(int j=0; dupCkField != null && j < dupCkField.length; j++)
					{
						if(Integer.toString(i).equals(dupCkField[j]))
							keyValue += paramSet.getString(i+1);
					}
					pstmt.setString(i + 1, paramSet.getString(i+1));
				}
				//
				//	Dup Check
				if(dupList.get(keyValue) == null)
				{
					dupList.put(keyValue, keyValue);
					//
					//	Batch
					pstmt.addBatch();
				}
				else
				{
					//
					//	�ߺ� �߻�
					dupCount++;
				}
				keyValue = "";
				if(term > 9999)
				//if(term > 1)
				{
					result = pstmt.executeBatch();
					//con.commit();
					pstmt.clearBatch();
					result_size = result_size + result.length;
					result = null;
					term = 0;
				}
			}		
			System.out.println("FETCH  SIZE [" + fetchSize + "] : ");
			if(dupCount > 0)
				System.out.println("PK DUP SIZE [" + dupCount + "] : ");
			//
			//	Batch
			result = pstmt.executeBatch();	
			pstmt.clearBatch();
			result_size = result_size + result.length;
			result = new int[result_size];
			
			pstmt.close();
		} catch(SQLException e) {
			//System.err.println(e.toString());
			throw e;
		}
		return result;
	}		
	/**
	 * 
	 * @param con
	 * @param sql
	 * @param paramSet
	 * @return
	 * @throws SQLException
	 */
	public static int[] executeUpdate (Connection con, String sql, ResultSet paramSet) throws SQLException {
		PreparedStatement pstmt = null;
		int result[] = null;
		int nRst = 0;
		int nCount = 0;
		int fieldSize = 0;
		int fetchSize = 0;
		try {			
			pstmt = con.prepareStatement(sql);

			while(paramSet.next())
			{
				fetchSize++;
				fieldSize = paramSet.getMetaData().getColumnCount();
				for (int i = 0; i < fieldSize; i++) {
					//System.out.println("VOLT_ID : " + paramSet.getString(i+1));
					pstmt.setString(i + 1, paramSet.getString(i+1));
				}
				//
				//	Batch
				//pstmt.addBatch();
				nRst = pstmt.executeUpdate();
				if(nRst > 0)
					nCount++;
			}
			result = new int[nCount];			
			System.out.println("FETCH SIZE [" + fetchSize + "] : ");
			//
			//	Batch
			//result = pstmt.executeBatch();
		} catch(SQLException e) {
			//System.err.println(e.toString());
			throw e;
		}
		return result;
	}
	public static int executeSP (Connection con, String spName, String[] params) {
		CallableStatement cs = null;
		try {
			//stored procedure ȣ��
			cs = con.prepareCall( "{ call " + spName + " }" );
			
			for (int i = 0; i < params.length; i++) {
				cs.setString(i + 1, params[i]);
			}

			cs.execute();
		} catch(Exception e) {
			System.err.println(e.toString());
		} finally {	
			try {
				cs.close();
			} catch(SQLException ex){
				System.err.println(ex.toString());
			}
		}
		return 0;
	}
	
	public static int executeSP (Connection con, String spName) {
		return executeSP(con, spName, new String[] {});
	}
	/**
	 * 
	 * @param conn Connection
	 * @param szSQL
	 * @param szIf
	 * @param contents
	 * @return
	 * @throws Exception
	 */
   public static boolean setCLOB(Connection conn, String szSQL, String[] szIf, String contents) throws Exception {
        boolean bsuccess = false;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Clob clob = null;
		//szSQL = "SELECT DE_INTRO FROM VOLTNVOLNINTRO WHERE VOLN_INTRO_SEQ='1' FOR UPDATE";
		//OutputStream OutputStream = null;		
   		try{ 
			pstmt = conn.prepareStatement(szSQL);
			int j=0;
			for(int i=0; i < szIf.length; i++)
			{
				pstmt.setString(j+1, szIf[i]);
				j++;
			}
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				clob = (CLOB)rset.getClob(1);
				Writer writer  = ((oracle.sql.CLOB)clob).getCharacterOutputStream();
				Reader src = new CharArrayReader(contents.toCharArray());
				
				
				try {
					int read = 0;
					char[] buffer = new char[1024];
					while ((read = src.read(buffer, 0, 1024)) != -1)
						writer.write(buffer, 0, read);
					
					src.close();
					writer.close();
					
					bsuccess = true;
				} catch (Exception e) {	
					bsuccess = false;
					System.out.println("== error setCLOB writer in MemoDAO ===");
					e.printStackTrace();
					//throw new Exception(e.getMessage());
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally {
		    try {
				if(pstmt != null) pstmt.close();	
				if(rset != null) rset.close();	
			} catch(Exception e) {
				bsuccess = false;
			}
		}
		
		return bsuccess;
	}		
}