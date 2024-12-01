package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.TjReportDao;
import net.sinodata.business.service.TjReportService;

@Service
public class TjReportServiceImpl implements TjReportService {

	@Autowired
	private TjReportDao tjReportDao;

	@Override
	public List<Map<String, Object>> getZl() {
		return tjReportDao.getZl();
	}

	@Override
	public List<Map<String, Object>> fwCountByMonth() {
		return tjReportDao.fwCountByMonth();
	}

	@Override
	public List<Map<String, Object>> jgCountByMonth() {
		return tjReportDao.jgCountByMonth();
	}

	// ==========================================================
	@Override
	public List<Map<String, Object>> tjsz() {
		return tjReportDao.tjsz();
	}

	@Override
	public List<Map<String, Object>> zcyhlRankByTimeAndJg() {
		return tjReportDao.zcyhlRankByTimeAndJg();
	}

	@Override
	public List<Map<String, Object>> yhdllRankByTimeAndJg() {
		return tjReportDao.yhdllRankByTimeAndJg();
	}

	@Override
	public List<Map<String, Object>> fwzclByJg() {
		return tjReportDao.fwzclByJg();
	}

	@Override
	public List<Map<String, Object>> fwzyRankByCzlx() {
		return tjReportDao.fwzyRankByCzlx();
	}

	@Override
	public List<Map<String, Object>> fwzyRankByYy() {
		return tjReportDao.fwzyRankByYy();
	}

	@Override
	public List<Map<String, Object>> bwcjlRankByJg() {
		return tjReportDao.bwcjlRankByJg();
	}

	@Override
	public List<Map<String, Object>> fwzyRankByJzfl() {
		return tjReportDao.fwzyRankByJzfl();
	}

	@Override
	public List<Map<String, Object>> qqlRankBySjAndJg() {
		// TODO Auto-generated method stub
		return tjReportDao.qqlRankBySjAndJg();
	}

	@Override
	public List<Map<String, Object>> fwzydylRank() {
		return tjReportDao.fwzydylRank();
	}

	@Override
	public List<Map<String, Object>> yyxtdylRank() {
		return tjReportDao.yyxtdylRank();
	}

	@Override
	public List<Map<String, Object>> zdyyqqlRank() {
		return tjReportDao.zdyyqqlRank();
	}

	@Override
	public List<Map<String, Object>> fwzyyclRank() {
		return tjReportDao.fwzyyclRank();
	}

	@Override
	public List<Map<String, Object>> fwzysygfsdRank() {
		return tjReportDao.fwzysygfsdRank();
	}

	@Override
	public List<Map<String, Object>> fwzyxysjRank() {
		return tjReportDao.fwzyxysjRank();
	}

}