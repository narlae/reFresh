package com.onlyfresh.devkurly.config;


import com.onlyfresh.devkurly.web.utils.JwtAuthenticationFilter;
import com.onlyfresh.devkurly.web.utils.JwtTokenProvider;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

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
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
                .mvcMatchers("/css/**","/icon/**","/imgs/**","/js/**", "/category/**", "/templates/**",
                        "/product/**", "/products/**", "/", "/home","/products/**").permitAll()
                .antMatchers("/resources").permitAll()
                .antMatchers("/login", "/loginForm").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/myPage/**","/address/**", "/order/**", "/logout").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/loginForm")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("tokenInfo")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}