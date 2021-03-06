package com.sbs.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sbs.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(@Param("memberId") int memberId, @Param("boardId") int boardId, @Param("title") String title, @Param("body") String body);

	public Article getForPrintArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart, int limitTake);

	public int getLastInsertId();

	public int getAritlceConuts(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			""")
	public int increaseHitCount(int id);

	@Select("""
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			""")
	public int getArticleHitCount(int id);
	
	

	@Update("""
			UPDATE article
			SET goodReactionPoint  = goodReactionPoint  + 1
			WHERE id = #{id}
			""")
	public int increaseGoodReactionPoint(int id);

	
	@Update("""
			UPDATE article
			SET badReactionPoint  = badReactionPoint  + 1
			WHERE id = #{id}
			""")
	public int increaseBadReactionPoint(int id);

	@Update("""
			UPDATE article
			SET goodReactionPoint  = goodReactionPoint  - 1
			WHERE id = #{id}
			""")
	public int decreaseGoodReactionPoint(int relId);

	@Update("""
			UPDATE article
			SET badReactionPoint  = badReactionPoint  - 1
			WHERE id = #{id}
			""")
	public int decreaseBadReactionPoint(int relId);

	
	@Select("""
			SELECT *
			FROM article
			WHERE id = #{relId}
			""")
	public Article getArticle(int relId);


}
