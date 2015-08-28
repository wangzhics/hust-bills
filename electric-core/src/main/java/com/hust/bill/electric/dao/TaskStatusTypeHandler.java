package com.hust.bill.electric.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.hust.bill.electric.bean.task.TaskStatus;

public class TaskStatusTypeHandler extends BaseTypeHandler<TaskStatus> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, TaskStatus parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public TaskStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		return TaskStatus.parser(i);
	}

	@Override
	public TaskStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		return TaskStatus.parser(i);
	}

	@Override
	public TaskStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		return TaskStatus.parser(i);
	}

}
