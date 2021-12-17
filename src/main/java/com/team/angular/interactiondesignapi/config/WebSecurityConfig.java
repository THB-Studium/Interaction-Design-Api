package com.team.angular.interactiondesignapi.config;

import com.team.angular.interactiondesignapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * WebSecurityConfigurerAdapter is the crux of our security implementation. 
 * It provides HttpSecurity configurations to configure cors, csrf, session management, rules for protected resources
 * 
*/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AdminService adminService;

	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().and().authorizeRequests().antMatchers("/**").permitAll()
				.anyRequest().authenticated();

	}

}
