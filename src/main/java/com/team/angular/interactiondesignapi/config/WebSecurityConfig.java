package com.team.angular.interactiondesignapi.config;

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().and().authorizeRequests().antMatchers("/**").permitAll()
				.anyRequest().authenticated();

	}

}
