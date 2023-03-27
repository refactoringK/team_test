package com.example.app.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.app.Execute;
import com.example.app.member.dao.MemberDAO;
import com.example.app.member.dto.MemberDTO;

public class LoginOkController implements Execute {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO = new MemberDTO();
		int memberNumber = 0;
		String path = null;
		String memberId = req.getParameter("memberId");
		String memberPassword = req.getParameter("memberPassword");
		String remember = req.getParameter("remember");
		HttpSession session = req.getSession();
		
		memberDTO.setMemberId(memberId);
		memberDTO.setMemberPassword(memberPassword);
		
		memberNumber = memberDAO.login(memberDTO);
		
		if(memberNumber == -1) {
			path = "/member/login.me?result=fail";
		}else {
			path = "/board/boardListOk.bo";
			
			session.setAttribute("memberNumber", memberNumber);
			
			if(remember != null) {
				Cookie cookie = new Cookie("memberId", memberId);
				cookie.setMaxAge(60 * 60 * 24);
				resp.addCookie(cookie);
			}
		}
		
		resp.sendRedirect(path);
	}
}
