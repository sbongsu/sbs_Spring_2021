<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물" />

<%@ include file="../common/head.jspf"%>
<script>
	const params = {};
	params.id = parseInt(${param.id});
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article_' + params.id + '_viewDone'
		
		if(localStorage.getItem(localStorageKey)){
			return;
		}
		
		localStorage.setItem(localStorageKey,true);
		
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode: 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	
	$(function() {
		// 실전코드
		// ArticleDetail__increaseHitCount();
		
		// 임시코드
		setTimeout(ArticleDetail__increaseHitCount, 500);
	})
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <div class="table-box-type-1">
      <table>
        <colgroup>
          <col width="200">
        </colgroup>
        <tbody>
          <tr>
            <th>번호</th>
            <td>${article.id}</td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${article.forPrintType2RegDate}</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${article.forPrintType2UpdateDate}</td>
          </tr>
          <tr>
            <th>조회수</th>
            <td>
              <span class="badge badge-primary article-detail__hit-count">${article.hitCount}</span>
            </td>
          </tr>
          <tr>
            <th>추천</th>
            <td>
            <span class="badge badge-primary">${article.goodReactionPoint}</span>
            <c:if test="${actorCanMakeReactionPoint}">
            <button class="btn btn-xs btn-primary">좋아요👍</button>
            <button class="btn btn-xs btn-secondary">싫어요👎</button>
            </c:if>
            </td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${article.extra__writerName}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>${article.title}</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>${article.body}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="btns mt-2">
      <c:if test="${ article.extra__actorCanDelete}">
        <a onclick="if(confirm('게시물을 수정하시겠습니까?') == false ){return false}" href="../article/modify?id=${article.id}"
          class="btn btn-outlineml-2"
        >게시물 수정</a>
      </c:if>
      <c:if test="${ article.extra__actorCanDelete}">
        <a onclick="if(confirm('게시물을 삭제하시겠습니까?') == false ){return false}" href="../article/doDelete?id=${article.id}"
          class="btn btn-outlineml-2"
        >게시물 삭제</a>
      </c:if>
      <button onclick="history.back()" class="btn btn-outline btn-secondary ml-2">뒤로가기</button>
    </div>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>