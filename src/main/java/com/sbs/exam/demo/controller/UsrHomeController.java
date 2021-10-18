package com.sbs.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	private int count;
	
	UsrHomeController(){
		 count = 0;
	}
	
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String show() {
		return "안녕하세요";
	}
	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String show2() {
		return "반갑습니다";
	}
	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public String show3() {
		return "잘가세요";
	}
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public int show4() {
		
		return count++;
	}
	@RequestMapping("/usr/home/main5")
	@ResponseBody
	public String show5() {
		count = 0;
		return "count의 값이 0으로 초기화 되었습니다.";
	}
}
