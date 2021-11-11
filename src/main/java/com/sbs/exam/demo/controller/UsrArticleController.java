package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인을 해주세요!");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body을(를) 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(isLoginedMemberId, title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(isLoginedMemberId, id);
		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("usr/article/list")
	public String showList(HttpSession httpSession, Model model) {

		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		List<Article> articles = articleService.getForPrintArticles(isLoginedMemberId);
		
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("usr/article/detail")
	public String showdetail(HttpSession httpSession, Model model, int id) {
		
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(isLoginedMemberId, id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@RequestMapping("usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpSession httpSession, Model model, int id) {
		
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(isLoginedMemberId, id);
		model.addAttribute("article", article);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}

	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인을 해주세요!");
		}
		Article article = articleService.getForPrintArticle(isLoginedMemberId, id);
		if (article == null) {
			return ResultData.from("F-2", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		if (article.getMemberId() != isLoginedMemberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}
		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다.", id), "id",id);
	}

	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
		boolean isLogined = false;
		int isLoginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			isLoginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (httpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", "로그인을 해주세요!");
		}
		
		Article article = articleService.getForPrintArticle(isLoginedMemberId, id);
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		if (article.getMemberId() != isLoginedMemberId) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		Article article2 = articleService.modifyArticle(isLoginedMemberId, id, title, body);
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다.", id), "article", article2);
	}
}