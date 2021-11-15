<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="로그인" />

<%@ include file="../common/head.jspf"%>


<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../member/doLogin">
      <table>
        <colgroup>
          <col width="100">
        </colgroup>
        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input name="loginId" type="text" class="w-96" / placeholder="로그인 아이디">
            </td>
          </tr>
          <tr>
            <th>비밀번호</th>
            <td>
              <input name="loginPw" type="password" class="w-96" / placeholder="로그인 비밀번호">
            </td>
          </tr>
        </tbody>
      </table>
      <div class="mt-2">
        <input type="submit" value="로그인" />
        <button type="button" onclick="history.back();">뒤로가기</button>
      </div>
    </form>

  </div>

  <%@ include file="../common/foot.jspf"%>