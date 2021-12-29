package com.sbs.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.exam.demo.service.ReplyService;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.ResultData;
import com.sbs.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {
	private ReplyService replyService;
	private Rq rq;

	public UsrReplyController(ReplyService replyService, Rq rq) {
		this.replyService = replyService;
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
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		if (Ut.empty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}
		
		return rq.jsReplace(writeRelyRd.getMsg(), replaceUri);
	}
	
	
}