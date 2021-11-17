<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="로그인" />

<%@ include file="../common/head.jspf"%>


<section class="mt-5">
  <form method="POST" action="../member/doLogin">
    <div class="form-control w-96 mx-auto">
      <label class="label">
        <span class="label-text">ID 로그인</span>
      </label>
      <input name="loginId" type="text" placeholder="아이디" class="input input-bordered w-96">

      <input name="loginPw" type="password" placeholder="비밀번호" class="input input-bordered w-96 mt-2">
      <div class="mt-2 text-center">
        <button type="submit" class="btn btn-outline">로그인</button>
        <button type="button" class="btn btn-outline btn-secondary" onclick="history.back();">뒤로가기</button>
      </div>
    </div>
  </form>


  <%@ include file="../common/foot.jspf"%>