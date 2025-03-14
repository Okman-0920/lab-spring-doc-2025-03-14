package com.ll.labspringdoc20250313.home.home.comtroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public String main () {
		return "API 서버 입니다";
	}
}
