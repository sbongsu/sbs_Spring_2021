package com.sbs.exam.demo.service;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.sbs.exam.demo.repository.ReplyRepository;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Member;
import com.sbs.exam.demo.vo.Reply;
import com.sbs.exam.demo.vo.ResultData;

@Service
public class ReplyService {
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> writeRely(int memberId, String relTypeCode, int relId, String body) {
		replyRepository.writeRely(memberId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
	}

	public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
		List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);

		for(Reply reply : replies) {
			updateForPrintData(actor, reply);
		}
		return replies;
	}

	public void updateForPrintData(Member actor, Reply reply) {
		if (reply == null) {
			return;
		}
		if (reply.getMemberId() == actor.getId()) {
			reply.setExtra__actorCanDelete(true);
		}		
	}

	public Reply getForPrintReply(Member actor, int id) {
		Reply reply = replyRepository.getForPrintReply(id);
		
		updateForPrintData(actor, reply);
		
		return reply;
	}

	public ResultData deleteReply(int id) {
		replyRepository.deleteReply(id);
		
		return ResultData.from("S-1", "댓글을 삭제했습니다!");
	}



}