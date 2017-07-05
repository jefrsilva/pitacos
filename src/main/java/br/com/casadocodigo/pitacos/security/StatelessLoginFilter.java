package br.com.casadocodigo.pitacos.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final UserDetailsService userDetailsService;
	private final TokenAuthenticationService tokenAuthenticationService;

	protected StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
			UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(urlMapping));

		this.tokenAuthenticationService = tokenAuthenticationService;
		this.userDetailsService = userDetailsService;

		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final LoginDTO loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				loginDTO.getEmail(), loginDTO.getSenha());

		return getAuthenticationManager().authenticate(loginToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authResult.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(userDetails);

		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}

}
