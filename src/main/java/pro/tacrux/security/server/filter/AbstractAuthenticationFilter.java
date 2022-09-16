/**
 *
 */
package pro.tacrux.security.server.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pro.tacrux.security.server.DefaultAuthenticationSuccessHandler;
import pro.tacrux.security.server.model.CredentialsVo;
import pro.tacrux.security.server.token.AuthenticationToken;
import pro.tacrux.security.util.JsonUtil;
import pro.tacrux.security.util.ReflectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * <b>认证授权过滤器接口.</b>
 * <b>Description:</b> 
 *
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022年5月5日 下午1:02:13
 * <b>Copyright:</b> Copyright tacrux All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022年5月5日 下午1:02:13    tacrux     new file.
 * </pre>
 */
public abstract class AbstractAuthenticationFilter<C extends CredentialsVo>
    extends AbstractAuthenticationProcessingFilter {

    public AbstractAuthenticationFilter(String pattern, String httpMethod) {
        this(new AntPathRequestMatcher(pattern, httpMethod));
    }

    public AbstractAuthenticationFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
        setAuthenticationSuccessHandler(new DefaultAuthenticationSuccessHandler());
    }

    private AuthenticationToken<C> getToken(HttpServletRequest request)
        throws InstantiationException, IllegalAccessException {

        C body = null;
        try {
            body = (C)JsonUtil.from(request.getInputStream(), ReflectUtils.getSuperClassGenericType(this.getClass(),0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (body == null) {
            throw new BadCredentialsException("请求参数不合法");
        }

        return new AuthenticationToken<>(body.getPrincipal(), body);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        try {
            return this.getAuthenticationManager().authenticate(getToken(request));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


}
