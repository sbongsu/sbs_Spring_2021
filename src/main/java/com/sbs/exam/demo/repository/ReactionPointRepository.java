package com.sbs.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
	@Select("""
			SELECT IFNULL(SUM(point),0) AS p
			FROM reactionPoint AS RP
			WHERE relTypeCode = #{relTypeCode} AND
			relId = #{relId} AND
			RP.memberId = #{memberId}
			""")
	public int getSumReactionPointByMemberId(int relId, String relTypeCode, int memberId);
}
