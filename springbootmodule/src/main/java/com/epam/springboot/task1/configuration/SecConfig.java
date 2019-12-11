package com.epam.springboot.task1.configuration;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//// Task 3:
//@Configuration
//@EnableWebSecurity
//public class SecConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("tom").password(passwordEncoder().encode("8888"))
//                .authorities("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/actuator/**").authenticated()
//                .and()
//                .httpBasic();
//
//        http.addFilterAfter(new CustomFilter(),
//                BasicAuthenticationFilter.class);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    private class CustomFilter extends GenericFilterBean {
//        @Override
//        public void doFilter(
//                ServletRequest request,
//                ServletResponse response,
//                FilterChain chain) throws IOException, ServletException {
//            chain.doFilter(request, response);
//        }
//    }
//
//}
