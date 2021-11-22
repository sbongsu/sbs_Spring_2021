<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 리스트" />

<%@ include file="../common/head.jspf"%>

<section class="mt-5">
  <div class="container mx-auto px-3">
  <div>
  게시물 갯수 : ${articleCounts } 개
  </div>
    <div class="toverflow-x-auto">
      <table class="table mx-auto w-2/3">
        <colgroup>
          <col width="80">
          <col width="150">
          <col width="150">
          <col width="150">
          <col>
        </colgroup>
        <thead>
          <tr class="text-center">
            <th>번호</th>
            <th>작성날짜</th>
            <th>수정날짜</th>
            <th>작성자</th>
            <th>제목</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="article" items="${articles}">
            <tr class="hover text-center">
              <td>${article.id}</td>
              <td>${article.regDate.substring(2,16)}</td>
              <td>${article.updateDate.substring(2,16)}</td>
              <td>${article.extra__writerName}</td>
              <td class="text-left">
                <a href="../article/detail?id=${article.id}" class="btn-text-link ml-2">${article.title}</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>