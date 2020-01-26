//package com.cogent.edgeserver.config;
//
////import com.cogent.adminservice.filter.TestFilter;
//
//import com.cogent.adminservice.filter.TestFilter;
//import com.cogent.contextserver.filter.UserContextFilter;
//import com.cogent.contextserver.security.JwtConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EdgeConfig {
////
////    @Autowired
////    private JwtConfig jwtConfig;
//
////    @Bean
////    public FilterRegistrationBean <UserContextFilter> filterRegistrationBean() {
////        FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean();
////        UserContextFilter customURLFilter = new UserContextFilter(jwtConfig);
////
////        registrationBean.setFilter(customURLFilter);
////        registrationBean.addUrlPatterns("/*");
////        registrationBean.setOrder(2); //set precedence
////        return registrationBean;
////    }
////
////    @Bean
////    public FilterRegistrationBean <TestFilter> testFilterRegistrationBean() {
////        FilterRegistrationBean <TestFilter> registrationBean = new FilterRegistrationBean();
////        TestFilter customURLFilter = new TestFilter();
////
////        registrationBean.setFilter(customURLFilter);
////        registrationBean.addUrlPatterns("/admin/*");
////        registrationBean.setOrder(3); //set precedence
////        return registrationBean;
////    }
//
//}
