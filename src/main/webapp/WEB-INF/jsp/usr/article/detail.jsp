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
		 ArticleDetail__increaseHitCount();
		
		// 임시코드
		//setTimeout(ArticleDetail__increaseHitCount, 500);
	})
</script>

<script>
  // 댓글작성 관련
  let ReplyWrite__submitFormDone = false; 
  function ReplyWrite__submitForm(form) {
    if ( ReplyWrite__submitFormDone ) {
      return;
    }
    
    // 좌우공백 제거
    form.body.value = form.body.value.trim();
    
    if ( form.body.value.length == 0 ) {
      alert('댓글을 입력해주세요.');
      form.body.focus();
      return;
    }
    
    if ( form.body.value.length < 2 ) {
      alert('댓글내용을 2자이상 입력해주세요.');
      form.body.focus();
      return;
    }
    
    ReplyWrite__submitFormDone = true;
    form.submit();
  }
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
                <a
                  href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
                  class="btn btn-xs btn-primary"
                >좋아요👍</a>
                <a
                  href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
                  class="btn btn-xs btn-secondary"
                >싫어요👎</a>
              </c:if>

              <c:if test="${actorCanCancelGoodReaction}">
                <a
                  href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
                  class="btn btn-xs btn-primary"
                >좋아요👍</a>
                <a onclick="alert(this.title); return false;" title="좋아요를 취소해주세요!!" href="#"
                  class="btn btn-xs btn-outline btn-secondary"
                >싫어요👎</a>
              </c:if>

              <c:if test="${actorCanCancelBadReaction}">
                <a onclick="alert(this.title); return false;" title="싫어요를 취소해주세요!!" href="#"
                  class="btn btn-xs btn-outline btn-primary"
                >좋아요👍</a>
                <a
                  href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
                  class="btn btn-xs btn-secondary "
                >싫어요👎</a>
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
            <td>${article.forPrintBody}</td>
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


<section class="mt-5">
  <div class="container mx-auto px-3">
    <h1>댓글작성</h1>
    <c:if test="${rq.isLogined()}">
      <form class="table-box-type-1" method="POST" action="../reply/doWrite"
        onsubmit="ReplyWrite__submitForm(this); return false;"
      >
        <input type="hidden" name="relTypeCode" value="article" />
        <input type="hidden" name="relId" value="${article.id}" />
        <div class="table-box-type-1">
          <table>
            <colgroup>
              <col width="200">
            </colgroup>
            <tbody>
              <tr>
                <th>작성자</th>
                <td>${rq.loginedMember.nickname}</td>
              </tr>
              <tr>
                <th>내용</th>
                <td>
                  <textarea class="w-full textarea textarea-bordered" name="body" rows="5" placeholder="내용"></textarea>
                </td>
              </tr>
              <tr>
                <th>댓글작성</th>
                <td>
                  <button type="submit" class="btn btn-primary">댓글작성</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </form>
  </div>
</section>
</c:if>
<c:if test="${rq.notLogined}">
  <a class="link link-primary" href="/usr/member/login">로그인</a> 후 이용해주세요.
    </c:if>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <h1>댓글 리스트(${replies.size()})</h1>
    <table class="table w-full table-fixed">
      <colgroup>
        <col width="50">
        <col width="100">
        <col width="100">
        <col width="50">
        <col width="100">
        <col width="150">
        <col />
      </colgroup>
      <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>추천수</th>
          <th>작성자</th>
          <th>비고</th>
          <th>내용</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="reply" items="${replies}">
          <tr>
            <td>${reply.id}</td>
            <td>${reply.forPrintType1RegDate}</td>
            <td>${reply.forPrintType1UpdateDate}</td>
            <td>${reply.goodReactionPoint}</td>
            <td>${reply.extra__writerName}</td>
            <td>
              <div class="btns mt-2">
                <c:if test="${ reply.extra__actorCanDelete}">
                  <a onclick="if(confirm('게시물을 수정하시겠습니까?') == false ){return false}"
                    href="../reply/modify?id=${reply.id}" class="btn btn-link"
                  >수정</a>
                </c:if>
                <c:if test="${ reply.extra__actorCanDelete}">
                  <a onclick="if(confirm('게시물을 삭제하시겠습니까?') == false ){return false}"
                    href="../reply/doDelete?id=${reply.id}" class="btn btn-link"
                  >삭제</a>
                </c:if>
              </div>
            </td>
            <td>${reply.forPrintBody}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

  </div>
</section>
<%@ include file="../common/foot.jspf"%>