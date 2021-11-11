package com.sbs.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int isLoginedMemberId;
	
	public Rq(HttpServletRequest req) {
		HttpSession httpSession = req.getSession();
		
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
	
	}
	
	
}
