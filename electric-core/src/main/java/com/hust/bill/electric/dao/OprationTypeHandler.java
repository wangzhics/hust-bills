package com.hust.bill.electric.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.hust.bill.electric.bean.task.Operation;


public class OprationTypeHandler extends BaseTypeHandler<Operation> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Operation parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public Operation getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		return Operation.parser(i);
	}

	@Override
	public Operation getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		return Operation.parser(i);
	}

	@Override
	public Operation getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		return Operation.parser(i);
	}

}
