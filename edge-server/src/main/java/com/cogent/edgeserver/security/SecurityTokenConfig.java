package com.cogent.edgeserver.security;

import com.cogent.contextserver.security.JwtConfig;
import com.cogent.edgeserver.filters.AddRequestHeaderFilter;
import com.cogent.edgeserver.modules.Modules;
import com.cogent.edgeserver.modules.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
@ComponentScan({
        "com.cogent.contextserver.security"
})
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    public SecurityTokenConfig(JwtConfig jwtConfig) {
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
                .exceptionHandling().authenticationEntryPoint(
                (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                .antMatchers(HttpMethod.GET, jwtConfig.getUri()).permitAll()
                .antMatchers(Modules.ACCOUNTING).hasRole(Roles.ACCOUNTING_ROLE)
                .antMatchers(Modules.PHARMACY).hasRole(Roles.PHARMACY_ROLE)
                .anyRequest().authenticated();
    }

    @Bean
    public AddRequestHeaderFilter addRequestHeaderFilter(JwtConfig jwtConfig) {
        return new AddRequestHeaderFilter(jwtConfig);
    }

}
