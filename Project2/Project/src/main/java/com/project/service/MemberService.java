package com.project.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mapper.MemberMapper;
import com.project.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final BCryptPasswordEncoder passwordEncoder;

	private final MemberMapper mMapper;

	@Transactional
	public void MemberAdd(Member m) {
		m.setM_pwd(passwordEncoder.encode(m.getM_pwd())); // request된 비밀번호의 입력값을 가져와 암호화시킨뒤 다시 저장,
		mMapper.MemberAdd(m);

	}

	public Member FindID(String m_email) {
		return mMapper.FindID(m_email);
	}

	public String FindAddr(String m_email) {
		return mMapper.FindAddr(m_email);
	}

	@Override
	public UserDetails loadUserByUsername(String m_email) throws UsernameNotFoundException {
		Member m = FindID(m_email);
		if (m != null) {
			return new UserDetail(m);
		}
		return null;

	}

	public void editMember(Member m) {
		m.setM_pwd(passwordEncoder.encode(m.getM_pwd()));
		m.setM_nickname(m.getM_nickname());
		m.setM_name(m.getM_name());
		m.setM_address(m.getM_address());
		m.setM_tel(m.getM_tel());
		m.setM_image(m.getM_image());
		mMapper.editMember(m);

	}

	/*** 이메일 인증 ****/

	@Transactional
	public void EmailNum(String m_code, String m_email) {
		mMapper.EmailNum(m_code, m_email);
	}

	public String EmailCode(String m_code, String m_email) {
		return mMapper.EmailCode(m_code, m_email);
	}

	public ArrayList<Member> AllMember() {
		return mMapper.AllMember();
	}

	public int emailCheck(String m_email) {
		int cnt = mMapper.emailCheck(m_email);
		System.out.println("이메일 중복체크 : " + cnt);
		return cnt;
	}

	public int nickCheck(String m_nickname) {
		int cnt2 = mMapper.nickCheck(m_nickname);
		System.out.println("닉네임 중복체크 :" + cnt2);
		return cnt2;
	}
}