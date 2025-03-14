package com.ll.labspringdoc20250313.home.home.comtroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "HomeController", description = "홈 컨트롤러")
@RestController
public class HomeController {

	@GetMapping(value = "/", produces = "test/html")
	@Operation(summary = "메인 페이지")
	public String home () {
		return "API 서버 입니다";
	}
}
