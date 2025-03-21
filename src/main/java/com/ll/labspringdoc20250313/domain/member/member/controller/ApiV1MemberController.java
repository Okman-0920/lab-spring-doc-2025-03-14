package com.ll.labspringdoc20250313.domain.member.member.controller;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ll.labspringdoc20250313.domain.member.member.dto.MemberDto;
import com.ll.labspringdoc20250313.domain.member.member.entity.Member;
import com.ll.labspringdoc20250313.domain.member.member.service.AuthTokenService;
import com.ll.labspringdoc20250313.domain.member.member.service.MemberService;
import com.ll.labspringdoc20250313.global.exceptions.ServiceException;
import com.ll.labspringdoc20250313.global.rq.Rq;
import com.ll.labspringdoc20250313.global.rsData.RsData;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;
    private final AuthTokenService authTokenService;

    record MemberJoinReqBody(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String nickname
    ) {
    }

    @PostMapping("/join")
    @Transactional
    public RsData<MemberDto> join(
        @RequestBody @Valid MemberJoinReqBody reqBody
    ) {
        Member member = memberService.join(reqBody.username, reqBody.password, reqBody.nickname);
        System.out.println(member.getCreateDate());
        return new RsData<>(
            "201-1",
            "%s님 환영합니다. 회원가입이 완료되었습니다.".formatted(member.getName()),
            new MemberDto(member));

    }

    record MemberLoginReqBody(
        @NotBlank String username,
        @NotBlank String password
    ) {
    }

    record MemberLoginResBody(
        MemberDto item,
        String apiKey,
        String accessToken
    ) {
    }

    @PostMapping("/login")
    @Transactional
    public RsData<MemberLoginResBody> login(
        @RequestBody @Valid MemberLoginReqBody reqBody
    ) {
        Member member = memberService
            .findByUsername(reqBody.username)
            .orElseThrow(
                () -> new ServiceException("401-1", "존재하지 않는 사용자입니다."));

        if (!member.matchPassword(reqBody.password))
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");

        String accessToken = memberService.genAccessToken(member);

        rq.setHeader("Authorization", "Bearer " + member.getApiKey() + " " + accessToken);

        rq.setCookie("accessToken", accessToken);
        rq.setCookie("apiKey", member.getApiKey());


        return new RsData<>(
            "200-1",
            "%s님 환영합니다.".formatted(member.getName()),
            new MemberLoginResBody(
                new MemberDto(member),
                member.getApiKey(),
                accessToken
            )
        );
    }

    @GetMapping("/me")
    @Transactional(readOnly = true)
    public MemberDto me() {
        Member member = rq.getActor();

        return new MemberDto(member);
    }

    @DeleteMapping("/logout")
    @Transactional(readOnly = true)
    public RsData<Void> logout() {
        rq.deleteCookie("accessToken");
        rq.deleteCookie("apiKey");

        return new RsData<>(
            "200-1",
            "로그아웃 되었습니다"
        );
    }


    // 테스트용 임시 함수
    @GetMapping("/userKey1")
    public String userKey1() {
        return UUID.randomUUID().toString();
    }
}
