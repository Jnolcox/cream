package com.tmm.frm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tmm.frm.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf()
        		.disable()
            .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/sign-up").permitAll()
                .antMatchers("/dashboard").authenticated()
                .antMatchers("/transactions/**").authenticated()
                .antMatchers("/api/accounts/**").authenticated()
                .antMatchers("/api/transactions/**").authenticated()
                .antMatchers("/api/tags/**").authenticated()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/loginprocess")
                .failureUrl("/?loginFailure=true")
                .permitAll();
    }
	
	 @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth
		 	.userDetailsService(userDetailsServiceImpl)
		 	.passwordEncoder(bCryptPasswordEncoder());
	 }
	 
	 @Bean 
	 public BCryptPasswordEncoder bCryptPasswordEncoder(){
		 return new BCryptPasswordEncoder();
	 }

}