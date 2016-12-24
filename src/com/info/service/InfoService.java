package com.info.service;

import java.util.List;

import com.info.model.Info;

public interface InfoService {
	List<Info> searchByForm(Info info);
	List<Info> searchByName(Info info);
	List<Info> searchByResume(Info info);
}
