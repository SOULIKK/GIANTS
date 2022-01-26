package com.spring.giants.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하는 회원이 아닙니다.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "회원정보가 일치하지 않습니다. 다시 시도해주세요.";
        } else {
            errorMessage = "요청이 유효하지 않습니다. 다시 시도해주세요.";
        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/auth/login?error=true&msg="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}