package com.info.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
public interface RowMpper<T> {

	public T mapperRow(ResultSet rs) throws SQLException;

}
