package br.com.casadocodigo.pitacos.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.MalformedJwtException;

public class TokenAuthenticationService {

	public static final String AUTH_HEADER_NAME = "Authorization";
	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, UserDetailsService userDetailsService) {
		this.tokenHandler = new TokenHandler(secret, userDetailsService);
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication) {
		final UserDetails userDetails = userAuthentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, "Bearer " + tokenHandler.createTokenForUser(userDetails));

	}

	public Authentication getAuthentication(HttpServletRequest httpRequest) {
		String authHeader = httpRequest.getHeader(AUTH_HEADER_NAME);

		if (authHeader != null) {
			String token = authHeader.replaceAll("Bearer ", "");
			try {
				final UserDetails userDetails = tokenHandler.parseUserFromToken(token);
				if (userDetails != null) {
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
