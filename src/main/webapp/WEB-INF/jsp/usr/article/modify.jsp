<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시물 수정" />
<%@ include file="../common/head.jspf"%>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../article/doModify">
      <input type="hidden" name="id" value="${article.id}" />
      <table class="border-collapse border-2 border-gray-500">
        <tbody>
          <tr>
            <th>번호</th>
            <td>${article.id}</td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${article.regDate.substring(2, 16)}</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${article.updateDate.substring(2, 16)}</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${article.extra__writerName}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input class="w-96 input input-bordered" name="title" type="text" placeholder="제목" value="${article.title}" />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea class="w-full textarea h-24 textarea-bordered" name="body" rows="10">${article.body}</textarea>
            </td>
          </tr>
          <tr>
            <th>수정</th>
            <td>
              <input class="btn btn-outline" type="submit" value="수정" />
              <button class="btn btn-outline btn-secondary" type="button" onclick="history.back();">뒤로가기</button>
             <!--  <c:if test="${article.extra__actorCanDelete}">
                <a class="btn btn-link" onclick="if ( confirm('정말 삭제하시겠습니까?') == false ) return false;"
                  href="../article/doDelete?id=${article.id}"
                >게시물 삭제</a>
              </c:if> -->
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>