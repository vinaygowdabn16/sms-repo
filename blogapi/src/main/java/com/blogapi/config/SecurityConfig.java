package com.blogapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


       http
               .csrf().disable()
               .authorizeRequests()
               .antMatchers(HttpMethod.GET,"/api/**").permitAll()
               .antMatchers("/api/auth/**").permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (request,response,authentication)->{
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("user successfully sign-out");

        };

    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.builder().username("pankaj").password(passwordEncoder().encode("password")).roles("USER").build();
//   UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//
//   return new InMemoryUserDetailsManager(user,admin);
//
//    }
}
