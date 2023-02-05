package com.onlyfresh.devkurly.config;


import com.onlyfresh.devkurly.web.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/login","POST");

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers("/css/**", "/icon/**", "/imgs/**", "/js/**", "/category/**", "/templates/**",
                        "/product/**", "/products/**", "/", "/home", "/products/**", "/boardlist/**", "/cart/**").permitAll()
                .antMatchers("/resources").permitAll()
                .antMatchers("/login", "/loginForm").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/myPage/**", "/address/**", "/order/**", "/logout", "/board/**").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/loginForm"))
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("tokenInfo")
                .invalidateHttpSession(false)
                .and()
                .addFilterAt(jwtIdPwdAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), JwtIdPwdAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtIdPwdAuthenticationFilter jwtIdPwdAuthenticationFilter() {
        JwtIdPwdAuthenticationFilter jwtIdPwdAuthenticationFilter = new JwtIdPwdAuthenticationFilter(LOGIN_REQUEST_MATCHER);
        jwtIdPwdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        jwtIdPwdAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        jwtIdPwdAuthenticationFilter.setAuthenticationManager(customAuthenticationManager());
        return jwtIdPwdAuthenticationFilter;
    }

    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomLoginSuccessHandler(jwtTokenProvider);
    }

    @Bean
    public CustomLoginFailureHandler authenticationFailureHandler(){
        return new CustomLoginFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}