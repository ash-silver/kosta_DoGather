package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
=======
>>>>>>> e378ef4a4bd4cdb302596152bec5eb70d14c2b52
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {
<<<<<<< HEAD

	
	@GetMapping("")
	public String MemberFormLogin() {
		return "login";
	}
	
	
	@PostMapping("")
	public String MemberFormLogin2() {
		return "login";
	}

	
=======
	
	
	@GetMapping("")
	public String myform() {
		
		return "mypage";
	}
>>>>>>> e378ef4a4bd4cdb302596152bec5eb70d14c2b52
}
