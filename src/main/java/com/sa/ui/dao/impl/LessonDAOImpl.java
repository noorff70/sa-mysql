package com.sa.ui.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sa.ui.dao.LessonDAO;
import com.sa.ui.model.Lesson;

@Transactional(readOnly = true)
@Repository
public class LessonDAOImpl implements LessonDAO {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public Lesson getLessonByCourseId(long courseId) {

		Lesson lesson = new Lesson();
		lesson.setLessonId(courseId);

		List<Map<String, Object>> head = jdbcTemplate.queryForList(
				"SELECT lessonTitle, lessonAuthor FROM lesson WHERE courseId = ?", courseId);
		if (head.isEmpty()) {
			return lesson;   // no content for this course: empty lists, not null
		}
		lesson.setLessonTitle((String) head.get(0).get("lessonTitle"));
		lesson.setLessonAuthor((String) head.get(0).get("lessonAuthor"));

		jdbcTemplate.query(
				"SELECT missionNo, description FROM lessonmission WHERE courseId = ? ORDER BY missionNo",
				rs -> {
					Lesson.LessonMission m = new Lesson.LessonMission();
					m.setId(rs.getInt("missionNo"));
					m.setDescription(rs.getString("description"));
					lesson.getLessonMission().add(m);
				}, courseId);

		// sections in order, each with its videos in order (a section may have no videos)
		Map<Integer, Lesson.LessonContent> sections = new LinkedHashMap<>();
		jdbcTemplate.query(
				"SELECT s.sectionId, s.title, v.name, v.lessonLink, v.lessonType "
				+ "FROM lessonsection s LEFT JOIN lessonvideo v ON v.sectionId = s.sectionId "
				+ "WHERE s.courseId = ? ORDER BY s.position, v.position",
				rs -> {
					int sectionId = rs.getInt("sectionId");
					Lesson.LessonContent section = sections.get(sectionId);
					if (section == null) {
						section = new Lesson.LessonContent();
						section.setLessonTitle(rs.getString("title"));
						sections.put(sectionId, section);
					}
					String name = rs.getString("name");
					if (name != null) {
						Lesson.LessonSubTitle video = new Lesson.LessonSubTitle();
						video.setName(name);
						video.setLessonLink(rs.getString("lessonLink"));
						video.setLessonType(String.valueOf(rs.getInt("lessonType")));
						section.getSubTitle().add(video);
					}
				}, courseId);
		lesson.getLessonContent().addAll(sections.values());

		return lesson;
	}
}
