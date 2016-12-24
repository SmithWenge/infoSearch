package com.info.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.info.dao.InfoDao;
import com.info.mapping.RowMpper;
import com.info.model.Info;
import com.info.util.Template;


public class InfoDaoImpl implements InfoDao {
	
	Template template = new Template();

	@Override
	public List<Info> searchByForm(Info info) {
		StringBuilder sql = new StringBuilder("SELECT typeName,auther,origin,publishDate,title,resume FROM info WHERE deleteFlag = 0 ");
        List<Object> list = new ArrayList<Object>();
        Optional<Info> optional = Optional.fromNullable(info);
        if (optional.isPresent()) {
            if (info.getTypeId() > 0) {
                sql.append(" AND typeId = ?");
                list.add(info.getTypeId());
            }
            if(info.getOriginId() >0) {
            	sql.append(" AND originId = ?");
            	list.add(info.getOriginId());
            }
            if(!info.getAuther().equals("") && info.getAuther() != null) {
            	sql.append(" AND auther = ?");
            	list.add(info.getAuther());
            }
            if(info.getStartDate() != null && !info.getStartDate().equals("")) {
            	sql.append(" AND DATE_FORMAT(publishDate,'%Y-%m-%d') >= ?");
            	list.add(info.getStartDate());
            }
            if(info.getStopDate() != null && !info.getStopDate().equals("")) {
            	sql.append(" AND DATE_FORMAT(publishDate,'%Y-%m-%d') <= ?");
            	list.add(info.getStopDate());
            }
        }
        
        Object[] args = list.toArray();
        sql.append(" ORDER BY publishDate");
        
        try {
        	return (List<Info>) template.query(sql.toString(), args, new SearchByFormRowMapper());
        } catch (Exception e) {
            return null;
        }
	}
	
	@Override
	public List<Info> searchByResume(Info info) {
		String sql = "SELECT typeName,auther,origin,publishDate,title,resume FROM info WHERE deleteFlag = 0 AND resume LIKE ? ESCAPE '\'";
		Object[] args = {
				info.getResume()
		};
		
		try {
			return (List<Info>) template.query(sql, args, new SearchByResumeRowMapper(info.getResume()));
        } catch (Exception e) {
            return null;
        }
	}
	
	@Override
	public List<Info> searchByName(Info info) {
		String sql = "SELECT typeName,auther,origin,publishDate,title,resume FROM info WHERE deleteFlag = 0 AND auther LIKE ? ESCAPE '\' ";
		Object[] args = {
				info.getAuther()
		};
		
		try {
			return (List<Info>) template.query(sql, args, new SearchByNameRowMapper(info.getAuther()));
        } catch (Exception e) {
            return null;
        }
	}
	
	private class SearchByFormRowMapper implements RowMpper<List<Info>> {

		@Override
		public List<Info> mapperRow(ResultSet rs) throws SQLException {
			List<Info> list = new ArrayList<Info>();
			while(rs.next()){
				Info info = new Info();
				info.setTypeName(rs.getString("typeName"));
				info.setAuther(rs.getString("auther"));
				info.setOrigin(rs.getString("origin"));
				info.setPublishDate(rs.getString("publishDate"));
				info.setTitle(rs.getString("title"));
				info.setResume(rs.getString("resume"));
				
				list.add(info);
			}
			
			return list;
		}
		
	}
	
	private class SearchByResumeRowMapper implements RowMpper<List<Info>> {
		
		private String name;
		
		public SearchByResumeRowMapper(String name) {
			String tmp = name.substring(1,name.length()-1); 
			//deal the "_","%","[","]","\" in result and the sequence can't be changed!
			//English of Chinese boy... sorry...
			tmp = tmp.replace("\\_", "_");
			tmp = tmp.replace("\\%", "%");
			tmp = tmp.replace("\\[", "["); 
			tmp = tmp.replace("\\]", "]");
			tmp = tmp.replace("\\\\", "\\");
			this.name = tmp;
		}
		
		@Override
		public List<Info> mapperRow(ResultSet rs) throws SQLException {
			List<Info> list = new ArrayList<Info>();
			while(rs.next()){
				Info info = new Info();
				info.setTypeName(rs.getString("typeName"));
				info.setAuther(rs.getString("auther"));
				info.setResume((rs.getString("resume")).replace(name, "<b style=\"color:red\">" + name + "</b>"));
				info.setOrigin(rs.getString("origin"));
				info.setPublishDate(rs.getString("publishDate"));
				info.setTitle(rs.getString("title"));
				
				list.add(info);
			}
			
			return list;
		}
	}
	
	private class SearchByNameRowMapper implements RowMpper<List<Info>> {
		
		private String name;
		
		public SearchByNameRowMapper(String name) {
			this.name = name.replace("%","");
		}
		
		@Override
		public List<Info> mapperRow(ResultSet rs) throws SQLException {
			List<Info> list = new ArrayList<Info>();
			while(rs.next()){
				Info info = new Info();
				info.setTypeName(rs.getString("typeName"));
				String auther = (rs.getString("auther")).replace(name, "<b style=\"color:red\">" + name + "</b>");
				info.setAuther(auther);
				info.setOrigin(rs.getString("origin"));
				info.setPublishDate(rs.getString("publishDate"));
				info.setTitle(rs.getString("title"));
				info.setResume(rs.getString("resume"));
				
				list.add(info);
			}
			
			return list;
		}
		
	}
}
