package com.team.angular.interactiondesignapi.config;

import com.team.angular.interactiondesignapi.config.authentication.CustomJwtAuthenticationFilter;
import com.team.angular.interactiondesignapi.config.authentication.JwtAuthenticationEntryPoint;
import com.team.angular.interactiondesignapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		/*http.cors().and().csrf().disable().exceptionHandling().and().authorizeRequests().antMatchers("/**").permitAll()
				.anyRequest().authenticated();
                .antMatchers("/**").hasAnyRole("ADMIN", "USER")*/

        //rollen müssen erweitert werden wegen add und soweiter

        // note: swagger; manage another mapping
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admins/*").hasRole("ADMIN")
                .antMatchers("/**").permitAll().anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
