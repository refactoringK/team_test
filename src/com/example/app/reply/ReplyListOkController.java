package com.example.app.reply;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.reply.dao.ReplyDAO;
import com.example.app.reply.vo.ReplyVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReplyListOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardNumber = Integer.valueOf(req.getParameter("boardNumber"));
		ReplyDAO replyDAO = new ReplyDAO();
		Gson gson = new Gson();
		JsonArray replies = new JsonArray();
		
//		List<ReplyVO> replyList = replyDAO.selectAll(boardNumber);
		
//		for(ReplyVO reply : replyList) {
//			String replyJson = gson.toJson(reply);
//			//json형식 문자열을 json요소로 변환시켜 저장한다.
//			replys.add(JsonParser.parseString(replyJson));
//		}
		
		replyDAO.selectAll(boardNumber).stream()
		.map(gson::toJson)
		.map(JsonParser::parseString)
		.forEach(replies::add);
		
		System.out.println(replies.toString());
		
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(replies.toString());
		out.close();
		
		
	}
}
