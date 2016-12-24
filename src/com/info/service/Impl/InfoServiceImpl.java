package com.info.service.Impl;

import java.util.List;

import com.info.dao.InfoDao;
import com.info.dao.Impl.InfoDaoImpl;
import com.info.model.Info;
import com.info.service.InfoService;

public class InfoServiceImpl implements InfoService {

	InfoDao infoDao = new InfoDaoImpl();
	
	@Override
	public List<Info> searchByForm(Info info) {
		return infoDao.searchByForm(info);
	}

	@Override
	public List<Info> searchByName(Info info) {
		return infoDao.searchByName(info);
	}

	@Override
	public List<Info> searchByResume(Info info) {
		return infoDao.searchByResume(info);
	}

}
