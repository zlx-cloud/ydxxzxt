package net.sinodata.business.util;

import java.util.ArrayList;
import java.util.List;


public class SearchResult {
	
	
	private int total;
	public SearchResult(List<?> data, Integer total) {
		this.rows = data;
		this.total = total;
	}
	private List<?> rows = new ArrayList();
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}



}
