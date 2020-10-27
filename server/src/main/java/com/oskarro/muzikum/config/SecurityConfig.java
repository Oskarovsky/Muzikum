package com.oskarro.muzikum.config;

import com.oskarro.muzikum.security.jwt.JwtAuthenticationEntryPoint;
import com.oskarro.muzikum.security.jwt.JwtAuthenticationFilter;
import com.oskarro.muzikum.user.UserDetailsServiceImpl;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * It provides default security configurations and allows other classes to extend it and
 * customize the security configurations by overriding its methods.
 *
 * The following class is the crux of security implementation
 *
 * This class implements Spring Security’s WebSecurityConfigurer interface.
 * It provides default security configurations and allows other classes to extend it
 * and customize the security configurations by overriding its methods.
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * To authenticate a User or perform various role-based checks, Spring security needs to load users details somehow.
     * For this purpose, It consists of an interface called UserDetailsService
     * which has a single method that loads a user based on username
     * */
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    /**
     * This class is used to return a 401 unauthorized error to clients
     * that try to access a protected resource without proper authentication.
     * It implements Spring Security’s AuthenticationEntryPoint interface
     * */
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;


    /**
     * Filter used for validating the access_token sent by the user
     * */
    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }


    /**
     * AuthenticationManagerBuilder is used to create an AuthenticationManager instance
     * which is the main Spring Security interface for authenticating a user.
     * */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    /**
     * Application uses the configured AuthenticationManager to authenticate a user in the login API.
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * The HttpSecurity configurations are used to configure security functionalities
     * and add rules to protect resources based on various conditions.
     * */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                            "/favicon.ico",
                            "/**.png",
                            "/**/*.gif",
                            "/**/*.svg",
                            "/**/*.jpg",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js").permitAll()
                    .antMatchers("/api/test/**").permitAll()
                    .antMatchers( "/api/**").permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/playlist/lastAdded/**").permitAll()
                    .antMatchers("/actuator/*").permitAll()
                    .antMatchers("/track/v2/*").permitAll()
                    .anyRequest().authenticated();
        // Add our custom JWT security filter
        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
