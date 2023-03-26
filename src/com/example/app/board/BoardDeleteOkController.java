package com.example.app.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.board.dao.BoardDAO;
import com.example.app.file.dao.FileDAO;
import com.example.app.file.dto.FileDTO;

public class BoardDeleteOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO boardDAO = new BoardDAO();
		FileDAO fileDAO = new FileDAO();
		int boardNumber = Integer.valueOf(req.getParameter("boardNumber"));
		final String UPLOAD_PATH = req.getSession().getServletContext().getRealPath("/") + "upload";
		//db에서 지운 파일들은 실제로도 삭제되어야한다.
		//file객체에 넣고 delete를 하면되는데 파일이 저장된 실제 이름이 필요하므로
		// 쿼리를 날려 가져온다.
		List<FileDTO> files = fileDAO.select(boardNumber);
		
//		for(FileDTO file : files) {
//			File temp = new File(uploadPath, file.getFileSystemName());
//			if(temp.exists()) {
//				temp.delete();
//			}
//		}
		
//		스트림과 람다식
		files.stream().map(file -> file.getFileSystemName())
		.map(name -> new File(UPLOAD_PATH, name))
		.filter(tmp -> tmp.exists())
		.forEach(tmp -> tmp.delete());
		
		
		fileDAO.delete(boardNumber);
		boardDAO.delete(boardNumber);
		
		resp.sendRedirect("/board/boardListOk.bo");
	}
}
