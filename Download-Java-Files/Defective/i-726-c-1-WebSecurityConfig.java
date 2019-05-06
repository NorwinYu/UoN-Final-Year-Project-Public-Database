package com.squirrel.courses.config;

import com.squirrel.courses.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

        http.authorizeRequests().antMatchers("/profile").access("hasAnyRole('ROLE_ADMIN', 'ROLE_LECTURER', 'ROLE_STUDENT')");

        //http.authorizeRequests().antMatchers("/userInfo").hasAnyRole("admin", "lecturer", "student");
        http.authorizeRequests().antMatchers("/course").access("hasAnyRole('ROLE_ADMIN', 'ROLE_LECTURER', 'ROLE_STUDENT')");

        // For ADMIN only.
        //http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
        //http.authorizeRequests().antMatchers("/admin").hasRole("admin");

        // For ROLE only.
        //http.authorizeRequests().antMatchers("/mockPage").access("hasRole('ROLE_STUDENT')");

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/allcourses")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/allcourses");

    }
}
