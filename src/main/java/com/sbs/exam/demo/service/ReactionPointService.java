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

	public boolean actorCanMakeReactionPoint(int actorId,String relTypeCode, int relId) {
		if(actorId == 0) {
			return false;
		}
		
		return reactionPointRepository.getSumReactionPointByMemberId(relId,relTypeCode, actorId) == 0;
		
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
