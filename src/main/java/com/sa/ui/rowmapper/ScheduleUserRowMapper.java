package com.sa.ui.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sa.ui.model.ScheduleUser;

public class ScheduleUserRowMapper implements RowMapper<ScheduleUser>{

	@Override
	public ScheduleUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ScheduleUser sUser = new ScheduleUser();
		sUser.setUserId(rs.getLong("userId"));
		sUser.setScheduleId(rs.getLong("scheduleId"));
		sUser.setSlId(rs.getLong("slId"));
				
		
		return sUser;
	}
}