package com.team.angular.interactiondesignapi.config;

import com.team.angular.interactiondesignapi.config.authentication.CustomJwtAuthenticationFilter;
import com.team.angular.interactiondesignapi.config.authentication.JwtAuthenticationEntryPoint;
import com.team.angular.interactiondesignapi.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

        // todo : test access role
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                //permitAll
                /*.antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST,"/reisers/**").permitAll()
                .antMatchers(HttpMethod.POST,"/buchungs/**").permitAll()
                .antMatchers(HttpMethod.POST,"/feedbacks/**").permitAll()
                //Admin
                .antMatchers("/admins/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/**").hasAnyRole()*/
                // but later the swaaager will only be accessible to admins
                .antMatchers("/swagger-ui.html/**","/authenticate").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
