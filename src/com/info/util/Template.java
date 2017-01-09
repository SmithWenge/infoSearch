package com.info.util;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.info.mapping.RowMpper;
import com.info.util.JdbcUtil;

public class Template {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

    //method to query.
	public Object query(String sql,Object[] params,RowMpper<?> rsh){
		
		try{
			conn = JdbcUtil.getInstance().getConnection();
			stmt = conn.prepareStatement(sql);
			ParameterMetaData pmd = stmt.getParameterMetaData();
			int count = pmd.getParameterCount();
			
			if(count>0){
				if(params==null||params.length<1)
					throw new IllegalArgumentException("The parameter is wrong");
				if(params.length!=count)
					throw new IllegalArgumentException("The parameter count is wrong");
				for(int i=0;i<count;i++){
					stmt.setObject(i+1, params[i]);
				}
			}
			
			rs = stmt.executeQuery();
			return rsh.mapperRow(rs);
		}catch(Exception e){
			throw new RuntimeException();
		}finally{
			JdbcUtil.getInstance().release(rs, stmt, conn);
		}
	}
}
