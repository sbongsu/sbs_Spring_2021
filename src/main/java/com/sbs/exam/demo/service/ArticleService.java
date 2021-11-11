package com.sbs.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbs.exam.demo.repository.ArticleRepository;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Article;
import com.sbs.exam.demo.vo.ResultData;

@Service
public class ArticleService {
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}


	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		
		int id = articleRepository.getLastInsertId();
		

		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
	}

	public List<Article> getForPrintArticles(int actorId) {
		
		List<Article> articles = articleRepository.getForPrintArticles();
		
		for(Article article : articles) {
			updatePrrintForData(actorId, article);
		}
		return articleRepository.getForPrintArticles();
	}

	public Article getForPrintArticle(int actorId, int id) {
		
		Article article = articleRepository.getForPrintArticle(id);
		
		updatePrrintForData(actorId, article);
		return article;
	}

	private void updatePrrintForData(int actorId, Article article) {
		
		if(article == null) {
			return;
		}
		if(article.getMemberId() == actorId) {
			article.setExtra__actorCanDelete(true);
		}
	}


	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
		
	}

	public Article modifyArticle(int actorId, int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		Article article =  getForPrintArticle(actorId, id);
		return article;
	}
}