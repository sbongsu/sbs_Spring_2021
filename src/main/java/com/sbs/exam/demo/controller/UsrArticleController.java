package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
		
		Rq rq = new Rq(req);
		

		if (rq.isLogined() == false) {
			return ResultData.from("F-A", "로그인을 해주세요!");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body을(를) 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getIsLoginedMemberId(), title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("usr/article/list")
	public String showList(HttpServletRequest req, Model model) {

		Rq rq = new Rq(req);
		
		List<Article> articles = articleService.getForPrintArticles(rq.getIsLoginedMemberId());
		
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("usr/article/detail")
	public String showdetail(HttpServletRequest req, Model model, int id) {
		
		Rq rq = new Rq(req);
		
		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@RequestMapping("usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, Model model, int id) {
		
		Rq rq = new Rq(req);
		
		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		model.addAttribute("article", article);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}

	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
	
		Rq rq = new Rq(req);

		if (rq.isLogined() == false) {
			return Ut.jsHistoryBack("로그인후 이용해주세요!");
		}
		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		if (article == null) {
			return Ut.jsHistoryBack("게시물이 존재하지 않습니다.");
		}
		if (article.getMemberId() != rq.getIsLoginedMemberId()) {
			return Ut.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}
		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제했습니다.", id), "/usr/article/list");
//		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다.", id), "id",id);
	}

	@RequestMapping("usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		
		Rq rq = new Rq(req);

		if (rq.isLogined() == false) {
			return ResultData.from("F-A", "로그인을 해주세요!");
		}
		
		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		if (article.getMemberId() != rq.getIsLoginedMemberId()) {
			return ResultData.from("F-2", "해당 게시물에 대한 권한이 없습니다.");
		}

		Article article2 = articleService.modifyArticle(rq.getIsLoginedMemberId(), id, title, body);
		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다.", id), "article", article2);
	}
}