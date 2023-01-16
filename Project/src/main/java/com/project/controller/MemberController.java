package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

	
	@GetMapping("")
	public String MemberFormLogin() {
		return "login";
	}
	
	
	@PostMapping("")
	public String MemberFormLogin2() {
		return "login";
	}

	
}
