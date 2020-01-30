//package com.cogent.adminservice.config;
//
//import com.cogent.adminservice.annotation.WithUser;
//import com.cogent.adminservice.utils.UserInfo;
//import com.cogent.genericservice.config.JwtConfig;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//public class WithUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
//
//
//    private final JwtConfig jwtConfig;
//
//    public WithUserHandlerMethodArgumentResolver(JwtConfig jwtConfig) {
//        this.jwtConfig = jwtConfig;
//    }
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        // We check if our parameter is exactly what we need:
//        return parameter.hasParameterAnnotation(WithUser.class) &&
//                parameter.getParameterType().equals(UserInfo.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter,
//                                  ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest,
//                                  WebDataBinderFactory binderFactory) throws Exception {
//
//        //take the request
//        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
//        log.info("****** USERNAME :: {}", httpServletRequest.getHeader("username"));
//        log.info ("***** AUTHORIZATION SERVER :: {}", httpServletRequest.getHeader("Authorization"));
//
////        String userName;
////
////        Object principal =
////                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////
////        if (principal instanceof UserDetails) {
////            userName = ((UserDetails) principal).getUsername();
////        } else {
////            userName = principal.toString();
////        }
////        log.info("*** USERNAME :: ** {}",httpServletRequest.getAttribute("username"));
//        //take our Authorization header from request and get access token
////        String authorizationHeader = httpServletRequest.getHeader(jwtConfig.getHeader());
////        authorizationHeader = authorizationHeader.replace(jwtConfig.getPrefix(), "");
////        //Using jjwt library parse our token and create Claim object
////        Claims claims = Jwts.parser()
////                .setSigningKey(jwtConfig.getSecret().getBytes()) //todo just example
////                .parseClaimsJws(authorizationHeader).getBody();
////        //create UserInfo object which we need to inject in our method
////        String username = (String) claims.get("username");
//        String username = httpServletRequest.getHeader("username");
//        return new UserInfo(username);
//    }
//}