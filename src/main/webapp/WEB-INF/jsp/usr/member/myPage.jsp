<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.sbs.exam.demo.util.Ut"  %>
<c:set var="pageTitle" value="마이페이지" />

<%@ include file="../common/head.jspf"%>


<section class="mt-5">
  <div class="container mx-auto px-3">
    <div class="table-box-type-1">
      <table class="border-collapse border-2 border-gray-500">
        <colgroup>
          <col width="200">
          <col />
        </colgroup>
        <tbody>
          <tr>
            <th>아이디</th>
            <td>${member.loginId}</td>
          </tr>
          <tr>
            <th>이름</th>
            <td>${member.name}</td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td>${member.nickname}</td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>${member.email}</td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>${member.cellphoneNo}</td>
          </tr>

        </tbody>
      </table>
    </div>
        <div class="btns mt-2">
      <a href="../member/checkPassword?replaceUri=${Ut.getUriEncoded('../member/modify')}" class="btn btn-outline">회원정보수정</a>
      <button class="btn btn-outline" type="button"
        onclick="history.back();">뒤로가기</button>

    </div>
  </div>
</section>


<%@ include file="../common/foot.jspf"%>