package org.max.sp;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Maksym_Bondarenko on 1/25/2016.
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig() {
        super(true);
    }

    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().authorities("ROLE_ANON");
        http
                .exceptionHandling().and()
                //.headers().and()
                .authorizeRequests().antMatchers("/get").hasRole("ANON");

        http.authorizeRequests()
                .antMatchers("/getsecured").hasRole("USER").and().httpBasic();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // enable in memory based authentication with a user named
                // "user" and "admin"
                .inMemoryAuthentication().withUser("user").password("pp").roles("USER").and()
                .withUser("admin").password("ppaa").roles("USER", "ADMIN");
    }
}
