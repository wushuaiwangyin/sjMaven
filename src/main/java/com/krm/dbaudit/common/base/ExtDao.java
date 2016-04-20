package com.krm.dbaudit.common.base;
import java.util.List;

public interface ExtDao {
	public abstract void executeBySQLText(String sqlText);
	
	public abstract void insertBySQLText(String sqlText);
	
    public abstract List queryBySQLText(String s, Object aobj[]);

    public abstract List queryBySQLText(String s);

    public abstract int updateBySQLText(String s, Object aobj[]);

    public abstract int updateBySQLText(String s);

    public abstract String queryForString(String s, String s1, Object aobj[]);

    public abstract String queryForString(String s, String s1);

    public abstract int queryForInt(String s, Object aobj[]);

    public abstract int queryForInt(String s);

    public abstract long queryForLong(String s, Object aobj[]);

    public abstract long queryForLong(String s);
    
    public abstract void commit();
}
