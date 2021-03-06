package br.com.casadocodigo.pitacos.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final TokenAuthenticationService tokenAuthenticationService;

	public SecurityConfiguration(UserDetailsService userDetailsService,
			TokenAuthenticationService tokenAuthenticationService) {
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers("/api/v1/public/login").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/pitacos/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/curtidas/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/usuarios/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf().disable()
			.addFilterBefore(new StatelessLoginFilter("/api/v1/login", tokenAuthenticationService, userDetailsService, 
					authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
