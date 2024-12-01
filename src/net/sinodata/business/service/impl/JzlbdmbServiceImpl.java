package net.sinodata.business.service.impl;

import java.util.List;

import net.sinodata.business.dao.JzlbdmbDao;
import net.sinodata.business.entity.Jzlbdmb;
import net.sinodata.business.service.JzlbdmbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JzlbdmbServiceImpl implements JzlbdmbService {
	
	@Autowired
	private JzlbdmbDao JzlbdmbDao;

	@Override
	public int insert(Jzlbdmb record) {
		// TODO Auto-generated method stub
		return JzlbdmbDao.insert(record);
	}

	@Override
	public int insertSelective(Jzlbdmb record) {
		// TODO Auto-generated method stub
		return JzlbdmbDao.insertSelective(record);
	}

	@Override
	public List<Jzlbdmb> queryJzlbdmb() {
		// TODO Auto-generated method stub
		return JzlbdmbDao.queryJzlbdmb();
	}

	
}
