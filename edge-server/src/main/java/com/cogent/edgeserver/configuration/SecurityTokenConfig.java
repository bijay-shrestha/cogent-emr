package com.cogent.edgeserver.configuration;

import com.cogent.edgeserver.JwtAuthenticationEntryPoint;
import com.cogent.edgeserver.filters.AddRequestHeaderFilter;
import com.cogent.edgeserver.modules.ApplicationModules;
import com.cogent.edgeserver.modules.Modules;
import com.cogent.edgeserver.security.jwt.JwtTokenAuthenticationFilter;
import com.cogent.genericservice.security.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    private JwtConfig jwtConfig;

    public SecurityTokenConfig(@Lazy JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                .exceptionHandling().authenticationEntryPoint(
//                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                .antMatchers(HttpMethod.GET, jwtConfig.getUri()).permitAll()
                .antMatchers(Modules.ACCOUNTING).hasRole(ApplicationModules.ACCOUNTING_APPLICATION_MODULE)
                .antMatchers(Modules.PHARMACY).hasRole(ApplicationModules.PHARMACY_APPLICATION_MODULE)
                .anyRequest().authenticated();
    }

    @Bean
    public AddRequestHeaderFilter addRequestHeaderFilter(JwtConfig jwtConfig) {
        return new AddRequestHeaderFilter(jwtConfig);
    }

    @Bean
    public JwtConfig jwtConfig(){
        return new JwtConfig();
    }
}
