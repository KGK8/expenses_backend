package com.example.expenses.utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import java.util.concurrent.TimeUnit;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
	private static final String SECRET = "ABCDEFGHIKLMOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz_1234567890_!@#$%^&*()_+^%$&*&^%$#$%^";
	private static final long EXPIRATION_TIME = 12;

	public static String generateJWTToken(String email, String id) {
		return JWT.create().withClaim("email", email).withClaim("userId", id).withClaim("version", "1.0-Beta")
				.withIssuer("Expenses Application").withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(EXPIRATION_TIME)))
				.sign(Algorithm.HMAC256(SECRET));
	}

	public String validateToken(String token) {
		token = token.split(" ")[1];
		try {
			DecodedJWT tokens = JWT.decode(token);
			String userId = tokens.getClaim("userId").toString().replaceAll("\"", "");
			return userId;
		} catch (Exception e) {
			System.err.println(e);
			return "IN_VALID_TOKEN";
		}
	}
}
