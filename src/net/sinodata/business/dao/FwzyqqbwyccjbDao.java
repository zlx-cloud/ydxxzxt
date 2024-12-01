package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyqqbwyccjb;
import net.sinodata.business.entity.FwzyqqbwyccjbDownload;

public interface FwzyqqbwyccjbDao {

	public List<Fwzyqqbwyccjb> qyertList(Map<String, Object> condition);

	public int qyertListCount(Map<String, Object> condition);

	public List<FwzyqqbwyccjbDownload> getYcList(Map<String, Object> condition);

}
