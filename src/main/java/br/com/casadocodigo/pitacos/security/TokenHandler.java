package br.com.casadocodigo.pitacos.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHandler {

	private final String secret;
	private final UserDetailsService userService;

	public TokenHandler(String secret, UserDetailsService userService) {
		this.secret = secret;
		this.userService = userService;
	}

	public String createTokenForUser(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public UserDetails parseUserFromToken(String token) {
		System.out.println("Parseando o usuario do token: " + token);
		
		String userName = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		return userService.loadUserByUsername(userName);
	}

}
