///**
// *
// */
//package pro.tacrux.security.server.provider;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import pro.tacrux.security.model.LoginUser;
//import pro.tacrux.security.server.dao.AbstractUserDetailsService;
//import pro.tacrux.security.server.model.CredentialsVo;
//import pro.tacrux.security.server.token.AuthenticationToken;
//import pro.tacrux.security.util.ReflectUtils;
//
///**
// * 抽象用户提供者
// * @param <S> UserDetailService
// */
//@Slf4j
//public abstract class AbstractAuthenticationProvider<S extends AbstractUserDetailsService<C>,C extends CredentialsVo>
//    implements AuthenticationProvider<S> {
//
//    @Autowired
//    public S userDetailsService;
//
//    @Override
//    public final AuthenticationToken<C> authenticate(Authentication authentication) throws AuthenticationException {
//        if(authentication instanceof AuthenticationToken){
//            // TODO: 2022/9/15 抛错
//        }
//        AuthenticationToken<C> authenticationToken = (AuthenticationToken<C>)authentication;
//        LoginUser loginUser = userDetailsService.loadUserDetails(authenticationToken.getCredentials());
//        if(loginUser==null){
//            // TODO: 2022/9/15
//        }
//
//        //通过认证
//        authenticationToken.setDetails(loginUser);
//        authenticationToken.setAuthenticated(true);
//
//        return authenticationToken;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        Class<?> tokenClazz = ReflectUtils.getSuperClassGenericType(this.getClass(), 0);
//        return tokenClazz.isAssignableFrom(authentication);
//    }
//}
