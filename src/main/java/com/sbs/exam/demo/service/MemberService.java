package com.sbs.exam.demo.service;

import org.springframework.stereotype.Service;

import com.sbs.exam.demo.repository.MemberRepository;
import com.sbs.exam.demo.vo.Member;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		// 로그인 아이디 중복체크
		Member oldmember = memberRepository.getMemberByLoginId(loginId);
		if (oldmember != null) {
			return -1;
		}
		// 이름 + 이메일 중복체크
		oldmember = memberRepository.getMemberByNameAndEmail(name,email);
		if (oldmember != null) {
			return -2;
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		
		return memberRepository.getMemberById(id);
	}


}
