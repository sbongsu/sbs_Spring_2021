package com.sbs.exam.demo.service;

import org.springframework.stereotype.Service;

import com.sbs.exam.demo.repository.MemberRepository;
import com.sbs.exam.demo.util.Ut;
import com.sbs.exam.demo.vo.Member;
import com.sbs.exam.demo.vo.ResultData;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	private AttrService attrService;
	
	public MemberService(MemberRepository memberRepository, AttrService attrService) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		// 로그인 아이디 중복체크
		Member oldmember = getMemberByLoginId(loginId);
		if (oldmember != null) {
			return ResultData.from("F-7",Ut.f("해당 로그인 아이디(%s)는 이미 사용중입니다.", loginId));
		}
		// 이름 + 이메일 중복체크
		oldmember = memberRepository.getMemberByNameAndEmail(name,email);
		if (oldmember != null) {
			return ResultData.from("F-8", Ut.f("(%s)와 (%s)(은)는 이미 가입 된 회원의 이름과 이메일 입니다.", name, email));
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		int id = memberRepository.getLastInsertId();
		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}

	public Member getMemberById(int id) {
		
		return memberRepository.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return  memberRepository.getMemberByLoginId(loginId);
	}

	public ResultData modify(int id, String nickname, String loginPw, String email, String cellphoneNo) {

		memberRepository.modify(id, nickname, loginPw, email, cellphoneNo);
		return ResultData.from("S-1", "회원정보를 수정했습니다.");
	}

	public String genMemberModifyAuthKey(int actorId) {
		String memberModifyAuthKey = Ut.getTempPassword(10);

		attrService.setValue("member", actorId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Ut.getDateStrLater(60 * 5));

		return memberModifyAuthKey;
	}

	public ResultData checkMemberModifyAuthKey(int actorId, String memberModifyAuthKey) {
		String saved = attrService.getValue("member", actorId, "extra", "memberModifyAuthKey");

		if ( !saved.equals(memberModifyAuthKey) ) {
			return ResultData.from("F-1", "일치하지 않거나 만료되었습니다.");
		}

		return ResultData.from("S-1", "정상적인 코드입니다.");
	}


}
