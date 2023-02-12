package com.project.mapper;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.model.Member;

@Mapper
public interface MemberMapper {
	
	public void MemberAdd(Member m);

	public void oAuthAdd(Member m);
	
	public Member FindID(String m_email); 
	
	public String FindAddr(String m_email);
	
	public void editMember(Member m);
	
	
	/***** 이메일 인증 ******/
	public void EmailNum(@Param("m_code")String m_code,@Param("m_email")String email);
	
	public String EmailCode(@Param("m_code")String m_code,@Param("m_email")String email);
	
	public ArrayList<Member> AllMember();
	
	public int emailCheck(String m_email);
	
	public int nickCheck(String m_nickname);
}
