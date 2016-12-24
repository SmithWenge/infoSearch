package com.info.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.model.Info;
import com.info.service.InfoService;
import com.info.service.Impl.InfoServiceImpl;

@WebServlet("/searchByResumeServlet")
public class searchByResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public searchByResumeServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		InfoService service = new InfoServiceImpl();
		request.removeAttribute("infos");
		
		String content  = request.getParameter("content");
		List<Info> infos;
		if(content == null || content.equals("")) {
			infos = new ArrayList<Info>();
		} else {
			//deal the "_","%","[","]","\" and the sequence can't be changed! 
			//English of Chinese boy... sorry...
			content = content.replace("\\", "\\\\");
			content = content.replace("_", "\\_");
			content = content.replace("%", "\\%");
			content = content.replace("[", "\\["); 
			content = content.replace("]", "\\]");
			Info info = new Info();
			info.setResume("%" + content + "%");
			infos = service.searchByResume(info);
		}
		
		request.setAttribute("infos", infos);
		request.getRequestDispatcher("/listInfo.jsp").forward(request, response);
	}

}
