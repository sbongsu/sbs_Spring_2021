package com.sbs.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.exam.demo.service.MemberService;
import com.sbs.exam.demo.util.Ut;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int isLoginedMemberId;
	@Getter
	private Member loginedMember;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession httpSession;

	
	public Rq(HttpServletRequest req, HttpServletResponse res, MemberService memberService) {
		this.httpSession = req.getSession();
		this.req = req;
		this.res = res;
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined = isLogined;
		this.isLoginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		     
	}

	public void printHistoryBackJs(String msg) {
		res.setContentType("text/html; charset=utf-8");
		print(Ut.jsHistoryBack(msg));
		
	}
	
	public void print(String msg) {
		
		try {
			res.getWriter().append(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void login(Member member) {
		httpSession.setAttribute("loginedMemberId", member.getId());
		
	}

	public void logout() {
		httpSession.removeAttribute("loginedMemberId");
		
	}
	
	public String historyBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);

		return "common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
}
