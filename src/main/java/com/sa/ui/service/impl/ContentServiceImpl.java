package com.sa.ui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.ContentDAO;
import com.sa.ui.model.Contents;
import com.sa.ui.service.ContentService;



@Service("contentService")
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private ContentDAO contentDAO;
	
	@Override
	public List<Contents> getAllContentsById(long id) {

		return contentDAO.getAllContentsById(id);
	}
	
	@Override
	public List<Contents> getContentsListByStudentId(long studentId){
		
		return contentDAO.getContentsByContentIdList(studentId);
		
	}
	
	public List<Contents> getContentsListByContentDesc(String desc) {
		return contentDAO.getContentsListByContentDesc(desc);
	}

	/*public Boolean insertStudentContent(String userName, long contentId ) {
		return contentDAO.insertStudentContent(userName, contentId);
	}*/

}

