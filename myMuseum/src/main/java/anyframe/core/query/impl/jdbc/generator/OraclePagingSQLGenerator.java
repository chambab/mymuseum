package anyframe.core.query.impl.jdbc.generator;

public class OraclePagingSQLGenerator
  extends AbstractPagingSQLGenerator
{
  public String getPaginationSQL(String originalSql, Object[] originalArgs, int[] originalArgTypes, int pageIndex, int pageSize)
  {
    StringBuffer sql = new StringBuffer(" SELECT * FROM ( SELECT   INNER_TABLE.* , ROW_NUMBER() OVER () AS ROW_SEQ FROM ( \n");
    
    sql.append(originalSql);
    
    sql.append(" ) INNER_TABLE ) AA WHERE ROW_SEQ BETWEEN ? AND ?");
    

    return sql.toString();
  }
  
  public Object[] setQueryArgs(Object[] originalArgs, int pageIndex, int pageSize)
  {
    Object[] args = new Object[originalArgs.length + 3];
    for (int i = 0; i < originalArgs.length; i++) {
      args[i] = originalArgs[i];
    }
    args[originalArgs.length] = String.valueOf(new Long(pageIndex * pageSize));
    
    args[(originalArgs.length + 1)] = String.valueOf(new Long((pageIndex - 1) * pageSize + 1));
    
    args[(originalArgs.length + 2)] = String.valueOf(new Long(pageIndex * pageSize));
    

    return args;
  }
  
  public int[] setQueryArgTypes(int[] originalArgTypes)
  {
    int[] argTypes = new int[originalArgTypes.length + 3];
    for (int i = 0; i < originalArgTypes.length; i++) {
      argTypes[i] = originalArgTypes[i];
    }
    argTypes[originalArgTypes.length] = 12;
    argTypes[(originalArgTypes.length + 1)] = 12;
    argTypes[(originalArgTypes.length + 2)] = 12;
    
    return argTypes;
  }
}
