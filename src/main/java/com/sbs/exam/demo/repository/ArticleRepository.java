package com.sbs.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sbs.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article writeArticle(String title, String body);
	
	@Select("select * from article where id = #{id}")
	public Article getArticle(@Param("id") int id);

	
	@Delete("delete from article where id = #{id}")
	public void deleteArticle(@Param("id") int id);

	@Update("update article set updateDate - now(), title = #{title}, `body` = #{body} where id = #{id}")
	public void modifyArticle(@Param("id") int id,@Param("title") String title,@Param("body") String body);


	@Select("select * from article")
	public List<Article> getArticles();

}
