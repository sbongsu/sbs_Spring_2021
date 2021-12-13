package com.sbs.exam.demo.service;

import org.springframework.stereotype.Service;

import com.sbs.exam.demo.repository.ReactionPointRepository;
import com.sbs.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
	private ReactionPointRepository reactionPointRepository;
	private ArticleService articleService;
	
	public ReactionPointService(ReactionPointRepository reactionPointRepository, ArticleService articleService) {
		this.reactionPointRepository = reactionPointRepository;
		this.articleService = articleService;
	}

	public ResultData actorCanMakeReactionPoint(int actorId,String relTypeCode, int relId) {
		if(actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요");
		}
		
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(relId,relTypeCode, actorId);
		
		if(sumReactionPointByMemberId != 0 ) {
			return ResultData.from("F-2", "리액션이 불가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		} 
		
		return ResultData.from("S-1", "리액션이 가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		
	}

	public ResultData addGoodReaction(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReaction(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		}
		return ResultData.from("S-1", "좋아요 성공!");
	}

	public ResultData addBadReaction(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReaction(actorId, relTypeCode, relId);
		
		switch(relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}
		return ResultData.from("S-1", "싫어요 성공!");
		
	}


}
