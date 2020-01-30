package com.cogent.edgeserver.filters;

import com.cogent.genericservice.config.JwtConfig;
import com.cogent.edgeserver.modules.Modules;
import com.cogent.edgeserver.modules.Roles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@Import(JwtConfig.class)
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

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
                .addFilterAfter(new JwtRequestFilter(jwtConfig),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(POST, jwtConfig.getUri()).permitAll()
                .antMatchers(Modules.ACCOUNTING).hasRole(Roles.ACCOUNTING_ROLE)
                .antMatchers(Modules.ADMIN).hasRole(Roles.ADMIN)
                .antMatchers(Modules.PHARMACY).hasRole(Roles.PHARMACY_ROLE)
                .anyRequest().authenticated();
    }

}
