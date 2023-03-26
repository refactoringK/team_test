package com.example.app.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.board.dao.BoardDAO;
import com.example.app.board.vo.BoardVO;
import com.example.app.file.dao.FileDAO;
import com.example.app.file.dto.FileDTO;

public class BoardReadOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardNumber = Integer.valueOf(req.getParameter("boardNumber"));
		BoardDAO boardDAO = new BoardDAO();
		BoardVO boardVO = boardDAO.select(boardNumber);
		FileDAO fileDAO = new FileDAO();
		
		List<FileDTO> files = fileDAO.select(boardNumber);
		boardVO.setFiles(files);
		
		boardDAO.updateReadCount(boardNumber);
		
		String content = boardVO.getBoardContent();
		boardVO.setBoardContent(content);
		
		req.setAttribute("board", boardVO); 
		
		req.getRequestDispatcher("/app/board/boardRead.jsp").forward(req, resp);
	}
}














