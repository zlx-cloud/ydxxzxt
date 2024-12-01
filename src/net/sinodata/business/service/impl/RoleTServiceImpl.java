package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.RoleTDao;
import net.sinodata.business.entity.RoleT;
import net.sinodata.business.service.RoleTService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleTServiceImpl implements RoleTService	 {
	@Autowired
	private RoleTDao roleDao;

	@Override
	public SearchResult roleList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<RoleT> data=	roleDao.getRoleListByPage( condition);
		int  count=	roleDao.getRoleCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public int existUserWithRoleId(String roleid) {
		// TODO Auto-generated method stub
		return roleDao.existUserWithRoleId(roleid);
	}

	@Override
	public int deleteByPrimaryKey(String roleid) {
		// TODO Auto-generated method stub
		return roleDao.deleteByPrimaryKey(roleid);
	}

	@Override
	public int insert(RoleT record) {
		// TODO Auto-generated method stub
		return roleDao.insert(record);
	}

	@Override
	public int insertSelective(RoleT record) {
		// TODO Auto-generated method stub
		return roleDao.insertSelective(record);
	}

	@Override
	public List<RoleT> selectByPrimaryKey(String roleid) {
		// TODO Auto-generated method stub
		return roleDao.selectByPrimaryKey(roleid);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleT record) {
		// TODO Auto-generated method stub
		return roleDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RoleT record) {
		// TODO Auto-generated method stub
		return roleDao.updateByPrimaryKey(record);
	}
	
	
	
}
