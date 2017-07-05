package br.com.casadocodigo.pitacos.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.MalformedJwtException;

public class TokenAuthenticationService {

	public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, UserDetailsService userDetailsService) {
		this.tokenHandler = new TokenHandler(secret, userDetailsService);
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication) {
		final UserDetails userDetails = userAuthentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(userDetails));

	}

	public Authentication getAuthentication(HttpServletRequest httpRequest) {
		String token = httpRequest.getHeader(AUTH_HEADER_NAME);
		if (!StringUtils.hasText(token)) {
			token = httpRequest.getParameter(AUTH_HEADER_NAME);
		}
		if (token != null) {
			try {
				final UserDetails userDetails = tokenHandler.parseUserFromToken(token);
				if (userDetails != null) {
					System.out.println("*********** Autenticou!!");
					return new UserAuthentication(userDetails);
				}
			} catch (MalformedJwtException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
