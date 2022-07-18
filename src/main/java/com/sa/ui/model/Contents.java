package com.sa.ui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contents {

	private long contentId;
	private String contentName;
	private String contentDesc;
	private long tutorId;
	private long topicId;
}
