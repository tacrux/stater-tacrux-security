package pro.tacrux.security.server.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.model.CredentialsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为pro.tacrux.security.server.dao.AbstractUserDetailsService#loadUserDetails(pro.tacrux.security.server.model.LoginReqVo)装载了参数
 * 集成这个类实现pro.tacrux.security.server.dao.AbstractUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication, pro.tacrux.security.server.model.LoginReqVo, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)即可
 * @param <C>
 */
@Slf4j
public abstract class AbstractUserDetailsService<C extends CredentialsVo> implements UserDetailsService<C> {


    /**
     * 给自定义实现装载请求响应等参数
     * @param credentialsVo	认证请求参数
     * @return
     */
    @Override
    public final LoginUser loadUserDetails( C credentialsVo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 设置全部请求头
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
            response = ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        // 加载用户信息

        LoginUser loginUser = this.loadUserDetails(authentication, credentialsVo, request, response);

        // 执行钩子
        beforeVerification(loginUser, credentialsVo,request,response);
        verification(loginUser, credentialsVo,request,response);
        afterVerification(loginUser, credentialsVo,request,response);

        loginUser.setStoreId(request.getRequestedSessionId());
        return loginUser;
    }

    /**
     * 加载用户详情
     *
     * @param authentication 当前上下文中的授权认证信息
     * @param request
     * @param response
     * @return
     */
    public abstract LoginUser loadUserDetails(Authentication authentication, C credentialsVo, HttpServletRequest request,
        HttpServletResponse response);


    @Override
    public void afterVerification(LoginUser loginUser, C credentialsVo, HttpServletRequest request,
        HttpServletResponse response) {

    }

    @Override
    public void beforeVerification(LoginUser loginUser, C credentialsVo, HttpServletRequest request,
        HttpServletResponse response) {

    }
}
