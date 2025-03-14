package com.ll.labspringdoc20250313.global.initData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import com.ll.labspringdoc20250313.domain.member.member.service.MemberService;
import com.ll.labspringdoc20250313.domain.post.post.service.PostService;

import lombok.RequiredArgsConstructor;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class DevInitData {
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    @Lazy
    private DevInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            System.out.println("devInitDataApplicationRunner works!");
        };
    }
}
