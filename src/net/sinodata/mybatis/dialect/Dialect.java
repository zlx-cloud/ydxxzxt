package net.sinodata.mybatis.dialect;

public abstract class Dialect {

	public static enum Type {
		ORACLE
	}

	public abstract String getLimitString(String sql, int skipResults,
			int maxResults);

}
