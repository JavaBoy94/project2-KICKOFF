package org.project2.omwp2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private LoginService loginService;


    private final AuthenticationFailureHandler failureHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();

        // 권한
        http.authorizeHttpRequests()
                .antMatchers("/login","/member/signUp","/member/login","/member/findEmail","/member/findEmailOk").permitAll()  // 모든 유저 접근 가능
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/member/**").hasAnyRole("ADMIN","MEMBER")
                .antMatchers("/wish/**").hasAnyRole("ADMIN","MEMBER")
                .antMatchers("/review/**").hasAnyRole("ADMIN","MEMBER")
        ;


        // 로그인
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("mEmail") // 로그인시 해당하는 아이디 name->userEmail
                .passwordParameter("mPw")
                .defaultSuccessUrl("/", true)   // 성공시 URL
                .loginProcessingUrl("/loginOk") // POST 로 보내는 액션
                .failureHandler(failureHandler) // 로그인 실패시 에러메세지 출력
//              .failureForwardUrl("/member/login?login_error=1")    // 실패시 로그인페이지로 다시 이동
//              .failureUrl("/member/login")
                .defaultSuccessUrl("/index", true)   // 성공시 URL
                .and()
                .oauth2Login()
                .loginPage("/member/login")
        ;



        // 로그아웃
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");

        http.userDetailsService(loginService);

        return http.build();
    }


    @Bean   // 암호화
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
