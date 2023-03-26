package com.example.app.reply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.reply.dao.ReplyDAO;
import com.example.app.reply.dto.ReplyDTO;

public class ReplyWriteOkController implements Execute {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReplyDTO replyDTO = new ReplyDTO();
		req.setCharacterEncoding("utf-8");
		
		replyDTO.setBoardNumber(Integer.valueOf(req.getParameter("boardNumber")));
		replyDTO.setMemberNumber(Integer.valueOf(req.getParameter("memberNumber")));
		replyDTO.setReplyContent(req.getParameter("replyContent"));
		
		new ReplyDAO().insert(replyDTO);
	}

}
