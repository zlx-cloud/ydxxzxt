package net.sinodata.business.util;

public class Page {
	private int rows;// 单页多少条记录
	private int page;// 第几页，同start参数重复，可以选择其中一个使用
	@Override
	public String toString() {
		String str = "page : "+ page+" rows: "+rows;
		return str;
	}



	public int getRows() {
		return (page*rows)/*rows*/;
	}



	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return (page*rows-rows)/*(page-1)*rows*/;
	}


	public int getPage() {
		return page;
	}



	public void setPage(int page) {
		this.page = page;
	}

}
