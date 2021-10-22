package com.sbs.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sbs.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	// INSER INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, body = ?
	public Article writeArticle(String title, String body);
	
	@Select("select * from article where id = #{id}")
	public Article getArticle(@Param("id") int id);

	
	// DELETE * FROM article WHERE id = ?
	public void deleteArticle(int id);

	
	// UPDATE article SET regDate = NOW(), updateDate = NOW(), title = ? , body = ? WHERE id = ?
	public void modifyArticle(int id, String title, String body);

	
	// SELECT * FROM article
	public List<Article> getArticles();

}
