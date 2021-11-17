<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시물 수정" />

<%@ include file="../common/head.jspf"%>
<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../member/doModify">
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
            <td>${article.regDate.substring(2,16)}</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${article.updateDate.substring(2,16)}</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${article.extra__writerName}</td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input name="title" type="text" />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <input name="body" type="text" />
            </td>
          </tr>
        </tbody>
      </table>
      <button type="submit">수정</button>
      </form>
    </div>
    <div class="btns mt-2">
      <button onclick="history.back()" class="btn-text-link ml-2">뒤로가기</button>
    </div>
</section>
<%@ include file="../common/foot.jspf"%>