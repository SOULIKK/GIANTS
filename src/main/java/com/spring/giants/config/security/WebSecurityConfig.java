package com.spring.giants.config.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;
    private final AuthenticationFailureHandler customFailurHandler;
    private final UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/", "/search/**", "/stock/**", "/auth/login", "/auth/join", "/auth/loginCheck", "/css/**", "/js/**", "/bs5/**",
                        "/main/**", "/board/list", "/pick", "/disclosure/**", "/comment/ep",
                        "/cert/**", "/nickname/check", "/findpw", "/newPw"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .failureHandler(customFailurHandler)
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http
                .rememberMe()
                .key("")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(86400 * 30)
                .alwaysRemember(true)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }
}
