/*
 * Copyright 2017 Okta, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.okta.spring.example;

import com.okta.spring.example.handlers.CustomAccessDeniedHandler;
import com.okta.spring.example.filters.AuthenticationTokenFilter;
import com.okta.spring.example.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This example renders a custom login page (hosted within this application). You can use a standard login with less
 * code (if you don't need to customize the login page) see the 'basic' example at the root of this repository.
 */
@SpringBootApplication
public class HostedLoginCodeFlowExampleApplication {

    private final Logger logger = LoggerFactory.getLogger(HostedLoginCodeFlowExampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HostedLoginCodeFlowExampleApplication.class, args);
    }

    /**
     * Create an ApplicationListener that listens for successful logins and simply just logs the principal name.
     * @return a new listener
     */
    @Bean
    protected ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessEventApplicationListener() {
        return event -> logger.info("Authentication Success with principal: {}", event.getAuthentication().getPrincipal());
    }
    /**
     * Create an ApplicationListener that listens for error logins and simply just logs the principal name.
     * @return a new listener
     */
    @Bean
    protected ApplicationListener<AuthenticationFailureBadCredentialsEvent> authenticationFailureEventApplicationListener() {
        return event -> logger.info("Authentication failed with principal: {}", event.getAuthentication().getPrincipal());
    }

    @Configuration
    @EnableWebSecurity
    @Order(2)
    //@EnableGlobalMethodSecurity(prePostEnabled = true)
    static class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private AuthenticationService authenticationService;

        @Autowired
        public void setAuthenticationService(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder builder) throws Exception {
            //builder.inMemoryAuthentication().withUser("guest").password("{noop}guest1234").authorities("ROLE_USER");
            //builder.inMemoryAuthentication().withUser("admin").password("{noop}admin1234").authorities("ROLE_ADMIN");
            builder.authenticationProvider(authenticationService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                        .antMatchers("/","/home","/signup","/login","/static/**","/error/**","/error","/").permitAll()
                        .antMatchers(HttpMethod.POST, "/adduser").permitAll()
                        .antMatchers("/mfa").hasAuthority("ROLE_PRE_AUTH")
                        .antMatchers("/newuser").hasAuthority("ROLE_ADMIN")
                        .antMatchers("/users").hasAnyAuthority("ROLE_LOCAL","ROLE_USER","ROLE_ADMIN")
                        //.antMatchers("/users").authenticated()
                    .and()
                        .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                    .and()
                        .formLogin()
                        .loginPage("/login")
                        //.failureUrl("/error")
                        .defaultSuccessUrl("/mfa",true)
                        //.failureHandler(failureHandler())
                    .and()
                        .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/");
        }
        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new CustomAccessDeniedHandler();
        }
    }
    @Configuration
    @EnableWebSecurity
    @Order(1)
    static class RestApiSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationTokenFilter authFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    //.hasAuthority("ROLE_ADMIN")
                    .and()
                    .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

    }
}