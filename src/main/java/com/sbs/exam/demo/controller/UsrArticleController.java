package com.sbs.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.service.BoardService;
import com.sbs.exam.demo.service.ReactionPointService;
import com.sbs.exam.demo.service.ReplyService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.Board;
import com.sbs.exam.demo.vo.Reply;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	private BoardService boardService;
	private ReactionPointService reactionPointService;
	private ReplyService replyService;
	private Rq rq;


	public UsrArticleController(ArticleService articleService, BoardService boardService, ReactionPointService reactionPointService, ReplyService replyService, Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.reactionPointService = reactionPointService;
		this.replyService = replyService;
		this.rq = rq;
	}

	@RequestMapping("usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {
		return "/usr/article/write";
	}

	@RequestMapping("usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri) {

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("title을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body을(를) 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getIsLoginedMemberId(), boardId, title,
				body);
		int id = writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다.", id), replaceUri);
	}

	@RequestMapping("usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.historyBackOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
		}

		int articleCounts = articleService.getAritlceConuts(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsCountInApage = 10;

		int pagesCount = (int) Math.ceil((double) articleCounts / itemsCountInApage);

		List<Article> articles = articleService.getForPrintArticles(rq.getIsLoginedMemberId(), boardId,
				searchKeywordTypeCode, searchKeyword, itemsCountInApage, page);

		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("articleCounts", articleCounts);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		return "usr/article/list";
	}

	@RequestMapping("usr/article/detail")
	public String showdetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);

		model.addAttribute("article", article);
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMember(), "article", id);
		int repliesCount = replies.size();
		model.addAttribute("repliesCount", repliesCount);
		
		ResultData actorCanMakeReactionPointRd  = reactionPointService.actorCanMakeReactionPoint(rq.getIsLoginedMemberId(),"article", id);
		model.addAttribute("actorCanMakeReactionPoint", actorCanMakeReactionPointRd.isSuccess());
		
		if(actorCanMakeReactionPointRd.getResultCode().equals("F-2")) {
			int sumReactionPointByMemberId  = (int)actorCanMakeReactionPointRd.getData1();
			
			if(sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			}
			else {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}

		return "usr/article/detail";
	}
	
	@RequestMapping("usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseHitCountRd(int id) {
		
		ResultData<Integer> increaseHitCountRd= articleService.increaseHitCount(id);
		
		if(increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}
		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));
		
		rd.setData2("id",id);
		return rd;
	}

	@RequestMapping("usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		model.addAttribute("article", article);
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}

	@RequestMapping("usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);
		if (article == null) {
			return rq.jsHistoryBack("게시물이 존재하지 않습니다.");
		}
		if (article.getMemberId() != rq.getIsLoginedMemberId()) {
			return rq.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}
		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.f("%d번 게시물을 삭제했습니다.", id), "/usr/article/list");
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);

		if (article == null) {
			return rq.historyBackOnView(Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getIsLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.historyBackOnView(actorCanModifyRd.getMsg());
		}
		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getForPrintArticle(rq.getIsLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getIsLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		articleService.modifyArticle(rq.getIsLoginedMemberId(), id, title, body);
		return rq.jsReplace(Ut.f("%d번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
}