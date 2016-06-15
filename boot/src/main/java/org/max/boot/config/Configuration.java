package org.max.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Maksym_Bondarenko on 6/4/2016.
 */
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class Configuration extends WebSecurityConfigurerAdapter {

    public Configuration() {
        super(true);
    }

    @Autowired
    public static void configureGlobal(AuthenticationManagerBuilder bUilder) throws Exception {
        bUilder.inMemoryAuthentication().withUser("u");
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/jsondoc");
        web.ignoring().antMatchers("/static/**");
    }
}
