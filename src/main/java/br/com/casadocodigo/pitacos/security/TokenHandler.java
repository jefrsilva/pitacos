package br.com.casadocodigo.pitacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenHandler {

	private final String secret;
	private final UserDetailsService userService;

	@Autowired
	public TokenHandler(@Value("${segredo}") String secret, UserDetailsService userService) {
		this.secret = secret;
		this.userService = userService;
	}

	public String createTokenForUser(UserDetails userDetails) {
		// @formatter:off
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		// @formatter:on
	}

	public UserDetails parseUserFromToken(String token) {
		// @formatter:off
		String userName = Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		// @formatter:on
		return userService.loadUserByUsername(userName);
	}

}
