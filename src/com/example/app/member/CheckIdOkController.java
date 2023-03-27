package com.example.app.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.member.dao.MemberDAO;

public class CheckIdOkController implements Execute{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO memberDAO = new MemberDAO();
		resp.setContentType("text/html; charset=utf-8");
		
		PrintWriter out =resp.getWriter();
//		System.out.println(req.getParameter("memberId"));
		
		boolean isTrue = memberDAO.checkId(req.getParameter("memberId"));
		
		if(isTrue) {
			out.print("사용 가능한 아이디입니다.");
		} else {
			out.print("중복된 아이디입니다.");
		}
		System.out.println(isTrue);
		
		out.close();
	}
}
