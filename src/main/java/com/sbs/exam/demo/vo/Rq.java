package com.sbs.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.sbs.exam.demo.service.MemberService;
import com.sbs.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
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
		this.req.setAttribute("rq", this);
		     
	}

	public boolean isNotLogin() {
		return !isLogined;
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

		return "usr/common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		return currentUri;
	}

	public String getEncodedCurrentUri() {
		return Ut.getUriEncoded(getCurrentUri());
	}	
	
	//Rq 객체가 자연스럽게 생성되도록 유도하는 메서드 (로그인시 첫 화면에서 로그아웃으로 안뜨고 로그인으로 뜨는 오류해결.)
	//지우면 안됨
	//편의성을 높이기 위해 BeforeActionInterceptor 에서 호출 필요
	public void initOnBeforeActionInterceptor() {

	}
}
