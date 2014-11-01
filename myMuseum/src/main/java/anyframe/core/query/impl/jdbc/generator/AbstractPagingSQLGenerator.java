package anyframe.core.query.impl.jdbc.generator;

public abstract class AbstractPagingSQLGenerator
  implements IPagingSQLGenerator
{
  public abstract String getPaginationSQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, int paramInt1, int paramInt2)
    throws Exception;
  
  public String getCountSQL(String originalSql)
  {
    StringBuffer sql = new StringBuffer("SELECT count(*) FROM ( ");
    sql.append(originalSql);
    sql.append(" ) CNT ");
    return sql.toString();
  }
}
