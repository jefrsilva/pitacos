package br.com.casadocodigo.pitacos.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.MalformedJwtException;

@Component
public class TokenAuthenticationService {

	public static final String AUTH_HEADER_NAME = "Authorization";

	private final TokenHandler tokenHandler;

	@Autowired
	public TokenAuthenticationService(TokenHandler tokenHandler) {
		this.tokenHandler = tokenHandler;
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
