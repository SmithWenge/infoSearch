package com.info.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.info.model.Info;
import com.info.service.InfoService;
import com.info.service.Impl.InfoServiceImpl;



@WebServlet("/searchServlet")
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public searchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InfoService service = new InfoServiceImpl();
		request.removeAttribute("infos");
		request.setCharacterEncoding("UTF-8");
		
		Info info = new Info();
		info.setTypeId(Integer.parseInt(request.getParameter("type")));
		info.setAuther(request.getParameter("auther"));
		info.setOriginId(Integer.parseInt(request.getParameter("origin")));
		info.setStartDate(request.getParameter("startDate"));
		info.setStopDate(request.getParameter("stopDate"));
		
		List<Info> infos = service.searchByForm(info);
		request.setAttribute("infos", infos);
		
		request.getRequestDispatcher("/listInfo.jsp").forward(request, response);
	}

}
