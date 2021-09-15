package com.nistagram.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Location", "X-Requested-With", "Authorization", "Cache-Control", "Content-Type", "X-Total-Count", "allowedOriginPatterns"));
        config.addAllowedMethod(HttpMethod.OPTIONS.name());
        config.addAllowedMethod(HttpMethod.GET.name());
        config.addAllowedMethod(HttpMethod.PUT.name());
        config.addAllowedMethod(HttpMethod.POST.name());
        config.addAllowedMethod(HttpMethod.DELETE.name());
        source.registerCorsConfiguration("/**", config);
        return new org.springframework.web.filter.CorsFilter(source);
    }

    // Defining access rights to specific URLs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // comunication between client and server is stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // for unauthorized request send 401 error
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // don't authenticate this particular request
                .authorizeRequests()
                //.antMatchers("**").permitAll()
                .antMatchers(HttpMethod.POST,"/login" ,"/register","/user/search").permitAll()
                .antMatchers(HttpMethod.GET,"/user/getUser/**").permitAll()


                // all other requests need to be authenticated
                .anyRequest().authenticated().and();

        // intercept every request and add filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        //http.csrf().disable(); 
        http.cors().and().csrf().disable();

		/* http.headers().addHeaderWriter(
                 new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));*/

    }

    @Override
    public void configure(WebSecurity web) {
    }
}

