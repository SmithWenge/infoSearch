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

@WebServlet("/searchByNaServlet")
public class searchByNaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public searchByNaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		InfoService service = new InfoServiceImpl();
		request.removeAttribute("infos");
		
		String auther  = request.getParameter("auther");
		List<Info> infos;
		if(auther == null || auther.equals("")) {
			infos = new ArrayList<Info>();
		} else {
			//deal the "_","%","[","]","\" and the sequence can't be changed! 
			//English of Chinese boy... sorry...
			auther = auther.replace("\\", "\\\\");
			auther = auther.replace("_", "\\_");
			auther = auther.replace("%", "\\%");
			auther = auther.replace("[", "\\["); 
			auther = auther.replace("]", "\\]");
			Info info = new Info();
			info.setAuther("%" + auther + "%");
			infos = service.searchByName(info);
		}
		
		request.setAttribute("infos", infos);
		request.getRequestDispatcher("/listInfo.jsp").forward(request, response);
	}

}
