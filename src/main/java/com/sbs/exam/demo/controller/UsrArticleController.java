package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
			return ResultData.from("F-1", "로그인을 해주세요!");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body을(를) 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(isLoginedMemberId, title, body);
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getArticle(id);
		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {

		List<Article> getarticles= articleService.getArticles();
		return ResultData.from("S-1", "게시물 리스트", getarticles);
	}

	@RequestMapping("usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {
		Article article = articleService.getArticle(id);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}

	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		articleService.deleteArticle(id);
		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다.", id), id);
	}

	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		articleService.modifyArticle(id, title, body);
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다.", id), id);
	}
}