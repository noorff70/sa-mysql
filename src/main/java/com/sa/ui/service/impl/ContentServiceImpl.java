package com.sa.ui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.ui.dao.ContentDAO;
import com.sa.ui.model.Contents;
import com.sa.ui.model.UserAccessReturnObject;
import com.sa.ui.service.ContentService;



@Service("contentService")
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private ContentDAO contentDAO;
	
	/*@Override
	public List<Contents> getAllContentsById(long id) {

		return contentDAO.getAllContentsById(id);
	}*/
	
	@Override
	public List<Contents> getContentsByStudentId(long studentId){
		
		return contentDAO.getContentsByStudentId(studentId);
		
	}
	
	public List<Contents> getContentsListByContentDesc(String desc) {
		return contentDAO.getContentsListByContentDesc(desc);
	}

	@Override
	public UserAccessReturnObject addContentToUser(Long userId, Long contentId) {
		
		UserAccessReturnObject userAccessReturnObject =  
				contentDAO.addContentToUser(userId, Long.valueOf(contentId));
		return userAccessReturnObject;
	}

}

