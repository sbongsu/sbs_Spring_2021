<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원정보수정" />
<%@ include file="../common/head.jspf"%>

<script>
	let MypageModify__submitDone = false;
	function MypageModify__submit(form) {
		if (MypageModify__submitDone) {
			return;
		}

		form.nickname.value = form.nickname.value.trim();

		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요.')
			form.nickname.focus();

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('비밀번호 확인을 입력해주세요.')
				form.loginPwConfirm.focus();

				return;
			}
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.')
			form.email.focus();

			return;
		}

		form.cellphoneNo.value = form.cellphoneNo.value.trim();

		if (form.cellphoneNo.value.length == 0) {
			alert('휴대폰번호를 입력해주세요.')
			form.cellphoneNo.focus();

			return;
		}

		MypageModify__submitDone = true;
		form.submit();
	}
</script>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../member/doModify"
      onsubmit="MypageModify__submit(this); return false;"
    >
      <input type="hidden" name="id" value="${article.id}" />
      <table class="border-collapse border-2 border-gray-500">
        <tbody>
          <tr>
            <th>아이디</th>
            <td>${rq.loginedMember.loginId}</td>
          </tr>
          <tr>
            <th>이름</th>
            <td>${rq.loginedMember.name}</td>
          </tr>
          <tr>
            <th>닉네임</th>
            <td>
              <input name="nickname" type="text" class="w-96 input input-bordered" value="${rq.loginedMember.nickname }"
                placeholder="닉네임을 입력해주세요"
              />
            </td>
          </tr>
          <tr>
            <th>새 비밀번호</th>
            <td>
              <input name="loginPw" type="password" class="w-96 input input-bordered" placeholder="새 비밀번호를 입력해주세요" />
            </td>
          </tr>
          <tr>
            <th>새 비밀번호 확인</th>
            <td>
              <input name="loginPwConfirm" type="password" class="w-96 input input-bordered"
                placeholder="새 비밀번호를 한번 더 입력해주세요"
              />
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input name="email" type="text" class="w-96 input input-bordered" value="${rq.loginedMember.email }"
                placeholder="이메일을 입력해주세요"
              />
            </td>
          </tr>
          <tr>
            <th>휴대전화번호</th>
            <td>
              <input class="input input-bordered" name="cellphoneNo" placeholder="휴대전화번호를 입력해주세요." type="tel"
                value="${rq.loginedMember.cellphoneNo}"
              />
            </td>
          </tr>
          <tr>
            <th>수정</th>
            <td>
              <input class="btn btn-outline" type="submit" value="회원정보수정" />
              <button class="btn btn-outline btn-secondary" type="button" onclick="history.back();">뒤로가기</button>
            </td>
          </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>