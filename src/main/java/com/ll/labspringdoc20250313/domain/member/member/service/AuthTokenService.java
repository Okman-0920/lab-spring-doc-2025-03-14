package com.ll.labspringdoc20250313.domain.member.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ll.labspringdoc20250313.domain.member.member.entity.Member;
import com.ll.labspringdoc20250313.standard.util.Ut;

@Service
public class AuthTokenService {
	@Value("${custom.jwt.secretKey}")
	private String jwtSecretKey;

	@Value("${custom.accessToken.expirationSeconds}")
	private long accessTokenExpirationSeconds;

	String genAccessToken(Member member) {
		long id = member.getId();
		String username = member.getUsername();

		return Ut.jwt.toString(
			jwtSecretKey,
			accessTokenExpirationSeconds,
			Map.of("id", id, "username", username)
		);
	}

	Map<String, Object> payload(String accessToken) {
		Map<String, Object> parsedPayload = Ut.jwt.payload(jwtSecretKey, accessToken);

		if (parsedPayload == null) return null;

		long id = (long) (Integer) parsedPayload.get("id");
		String username = (String) parsedPayload.get("username");

		return Map.of("id", id, "username", username);
	}
}
