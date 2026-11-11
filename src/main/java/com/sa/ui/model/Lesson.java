package com.sa.ui.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
 * Course content for the lesson page. Same JSON as sa-mongo's Lessons:
 * { lessonId, lessonTitle, lessonAuthor, lessonMission[], lessonContent[ { lessonTitle, subTitle[] } ] }
 * A course without content is returned with empty lists (never null).
 */
@Getter
@Setter
public class Lesson {

	private long lessonId;              // = course.courseId
	private String lessonTitle;
	private String lessonAuthor;
	private List<LessonMission> lessonMission = new ArrayList<>();
	private List<LessonContent> lessonContent = new ArrayList<>();

	@Getter
	@Setter
	public static class LessonMission {
		private int id;
		private String description;
	}

	@Getter
	@Setter
	public static class LessonContent {     // a section in the lesson menu
		private String lessonTitle;
		private List<LessonSubTitle> subTitle = new ArrayList<>();
	}

	@Getter
	@Setter
	public static class LessonSubTitle {    // a video in a section
		private String name;
		private String lessonLink;          // YouTube video id
		private String lessonType;          // "0" or "1", a string as in sa-mongo
	}
}
