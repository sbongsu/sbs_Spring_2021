package com.sbs.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.exam.demo.service.ArticleService;
import com.sbs.exam.demo.service.ReplyService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.Reply;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {
	private ReplyService replyService;
	private Rq rq;
	private ArticleService articleService;

	public UsrReplyController(ReplyService replyService, ArticleService articleService, Rq rq) {
		this.replyService = replyService;
		this.articleService = articleService;
		this.rq = rq;
	}
	
	@RequestMapping("usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode을(를) 입력해주세요");
		}
		
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("relId을(를) 입력해주세요");
		}
		
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body을(를) 입력해주세요");
		}

		ResultData<Integer> writeRelyRd = replyService.writeRely(rq.getIsLoginedMemberId(), relTypeCode, relId,
				body);
		int id = writeRelyRd.getData1();

	
		if (Ut.empty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}
		
		return rq.jsReplace(writeRelyRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("id을(를) 입력해주세요");
		}
		

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글이 존재하지 않습니다.", id));
		}
		
		if(reply.isExtra__actorCanDelete() == false) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글을 삭제할 권한이 없습니다.", id));
		}

		ResultData deleteRd = replyService.deleteReply(id);
	
		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}
		
		return rq.jsReplace(deleteRd.getMsg(), replaceUri);
	}
	
	@RequestMapping("usr/reply/modify")
	public String modify(int id, Model model) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("id을(를) 입력해주세요");
		}
		

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);
		
		if(reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글이 존재하지 않습니다.", id));
		}
		
		if(reply.isExtra__actorCanDelete() == false) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글을 수정할 권한이 없습니다.", id));
		}
		
		String relDataTitle = null;
		
		switch (reply.getRelTypeCode()) {
		case "article":
			Article article = articleService.getArticle(reply.getRelId());
			relDataTitle = article.getTitle();
		}
		model.addAttribute("relDataTitle", relDataTitle);
		model.addAttribute("reply", reply);

		return "usr/reply/modify";
	}
	
	
}