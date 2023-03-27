package com.example.app.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.board.dao.BoardDAO;

public class BoardListOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO boardDAO = new BoardDAO();
		String temp = req.getParameter("page");
		
		int page = temp == null ? 1 : Integer.valueOf(temp);
		
		int	rowCount = 10;
		int pageCount = 5;

		int startRow = (page-1) * rowCount;
		
		Map<String, Integer> pageMap = new HashMap<>();
		pageMap.put("startRow", startRow);
		pageMap.put("rowCount", rowCount);
		
		int total = boardDAO.getTotal(); //+++++++++
		
		int endPage = (int)(Math.ceil(page / (double)pageCount) * pageCount);
		int startPage = endPage - (pageCount -1);
		int realEndPage = (int)Math.ceil(total / (double)rowCount);
		boolean prev = startPage > 1;
		endPage = endPage > realEndPage ? realEndPage : endPage;
		boolean next = endPage != realEndPage;
		
		
//		++++++++
		req.setAttribute("boardList", boardDAO.selectAll(pageMap));
		req.setAttribute("page", page);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("prev", prev);
		req.setAttribute("next", next);
//		+++++++++
		req.getRequestDispatcher("/app/board/boardList.jsp").forward(req, resp);
	}
}
