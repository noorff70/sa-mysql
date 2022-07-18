package com.sa.ui.service;

import java.util.List;

import com.sa.ui.model.Contents;


public interface ContentService {

	public List<Contents> getAllContentsById(long id);
	public List<Contents> getContentsListByStudentId(long studentId);
	public List<Contents> getContentsListByContentDesc(String desc);
	//public Boolean insertStudentContent(String userName, long contentId);

}
