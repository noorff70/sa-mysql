package com.sa.ui.dao;

import java.util.List;

import com.sa.ui.model.Contents;
import com.sa.ui.model.UserAccessReturnObject;

public interface ContentDAO {

	//public List<Contents> getAllContentsById(long id);
	public List<Contents> getContentsByStudentId(long studentId);
	public List<Contents> getContentsListByContentDesc(String desc);
	public UserAccessReturnObject addContentToUser(Long userId, Long contendId) ;
}
