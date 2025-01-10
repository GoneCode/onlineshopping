package com.onlineshopping.userService.security.config;

import com.onlineshopping.userService.security.service.jwt.AuthTokenFilter;
import com.onlineshopping.userService.security.service.jwt.AuthEntryPointJwt;
import com.onlineshopping.userService.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

     @Autowired
     private UserDetailsServiceImpl userDetailsServiceImpl;
     @Autowired
     private AuthEntryPointJwt authEntryPointJwt;

    private final AuthenticationEntryPoint unauthorizedHandler;

    public WebSecurityConfig(AuthenticationEntryPoint unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

     @Bean
     public AuthTokenFilter authenticationJwtTokenFilter() {
         return new AuthTokenFilter();
     }

     @Bean
     public DaoAuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         authProvider.setUserDetailsService(userDetailsServiceImpl);
         authProvider.setPasswordEncoder(passwordEncoder());
         return authProvider;
     }

     @Bean
     public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
         return configuration.getAuthenticationManager();
     }

     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
         http.csrf(csrf->csrf.disable())
                 .exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler))
                 .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                         .requestMatchers("/api/test/**","/api/onlineShop/**").permitAll().anyRequest().authenticated());

         http.authenticationProvider(authenticationProvider());
         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
         return http.build();
     }







}
