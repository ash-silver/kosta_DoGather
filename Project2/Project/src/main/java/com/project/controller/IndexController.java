package com.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.model.Member;
import com.project.service.EmailService;
import com.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller 
@RequestMapping("/member")
public class IndexController {

	@Autowired
	private final MemberService mService;

	@Autowired
	private final EmailService eService;

	@GetMapping("/index")
	public String index() {
		return "index"; 
	}

	@GetMapping("/login") 
	public String loginForm() {  
		return "loginForm"; 
	}
	
	@GetMapping("/join")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(Member member) { 
		mService.MemberAdd(member);
//		return "redirect:/login";  
		return "loginForm";
	}
	
	/*제대로 구현하기 전 로그아웃을 위한 페이지*/ 
	@GetMapping("/logout")
	public String logout() {
		return "home";
	}
	/*페이지이동 확인을 위한 임시페이지*/ 
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	

	@GetMapping("/update")
	public String updateForm(Principal principal, Model model) {
		Member member = mService.FindID(principal.getName());
		model.addAttribute("member", member);
		String address = mService.FindAddr(principal.getName());
		String[] addr = address.split("/");
		
		switch(addr.length) {
		case 2:
			model.addAttribute("addr1", addr[0]);
			model.addAttribute("addr2", addr[1]);
			break;
		case 3:
			model.addAttribute("addr1", addr[0]);
			model.addAttribute("addr2", addr[1]);
			model.addAttribute("addr3", addr[2]);
		break;
			
		}
		return "memberUpdate";
	}
	

	@PutMapping("/update")
	public String update(Member member, @RequestParam("addr1") String addr1, @RequestParam("addr2") String addr2,
			@RequestParam(required = false) String addr3) {
		String address = addr1  + "/" + addr2 + "/" + addr3;
		member.setM_address(address);
		mService.editMember(member);
//		return "redirect:/index";     // redirect에러뜸
		return "index";
	}
	
	
	@GetMapping("/findPwd")
	public String findPwd(){
		return "findPwd";
	}
	
	/* 이메일 중복확인 */
	@ResponseBody
	@PostMapping("/emailCheck")
	public int emailCheck(@RequestParam("m_email") String m_email) {
		int cnt = mService.emailCheck(m_email);
		return cnt;	
	}

 
	/* 인증번호 발송 */
	@ResponseBody
	@PostMapping("/sendEmail")
	public String EmailCheck(String m_email, Model model) throws Exception {
		String code = eService.sendSimpleMessage(m_email); // EmailService의 메일을 보내는 메서드를 작동.(리턴값으로 인증번호를 받아옴)
		// mService.EmailNum(code, m_email); // Email에 해당하는 DB의 컬럼에 code에 인증번호를 저장시켜준다.
		model.addAttribute("email", m_email); // 인증번호를 보낸 email의 값을 인증 체크시 사용하기 위해 View로 전송 (Hidden으로 값을 저장해둠)
		return code;
	}

	
	
}
