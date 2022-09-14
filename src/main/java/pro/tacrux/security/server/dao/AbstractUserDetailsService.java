package pro.tacrux.security.server.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.model.LoginReqVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为pro.tacrux.security.server.dao.AbstractUserDetailsService#loadUserDetails(pro.tacrux.security.server.model.LoginReqVo)装载了参数
 * 集成这个类实现pro.tacrux.security.server.dao.AbstractUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication, pro.tacrux.security.server.model.LoginReqVo, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)即可
 * @param <T>
 */
@Slf4j
public abstract class AbstractUserDetailsService<T extends LoginReqVo> implements UserDetailsService<T> {


    /**
     * 给自定义实现装载请求响应等参数
     * @param parameter	认证请求参数
     * @return
     */
    @Override
    public final LoginUser loadUserDetails( T parameter) {
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

        LoginUser loginUser = this.loadUserDetails(authentication, parameter, request, response);

        // 执行钩子
        beforeVerification(loginUser,parameter,request,response);
        verification(loginUser,parameter,request,response);
        afterVerification(loginUser,parameter,request,response);
        return loginUser;
    }

    /**
     * 加载用户详情
     *
     * @param authentication 当前上下文中的授权认证信息
     * @param parameter      认证请求参数
     * @param request
     * @param response
     * @return
     */
    public abstract LoginUser loadUserDetails(Authentication authentication, T parameter, HttpServletRequest request,
        HttpServletResponse response);


    @Override
    public void afterVerification(LoginUser loginUser, T parameter, HttpServletRequest request,
        HttpServletResponse response) {

    }

    @Override
    public void beforeVerification(LoginUser loginUser, T parameter, HttpServletRequest request,
        HttpServletResponse response) {

    }
}
