/**
 * 
 */
package pro.tacrux.security.server.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pro.tacrux.security.server.model.LoginReqVo;
import pro.tacrux.security.server.token.AuthenticationToken;
import pro.tacrux.security.util.JsonUtil;

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
public abstract class AbstractAuthenticationFilter<REQ extends LoginReqVo<TOKEN>,TOKEN extends AuthenticationToken> extends AbstractAuthenticationProcessingFilter{

	protected boolean postOnly = true;

	protected AbstractAuthenticationFilter(String pattern, String httpMethod, boolean postOnly) {
		this(new AntPathRequestMatcher(pattern, httpMethod), postOnly);
	}

	protected AbstractAuthenticationFilter(RequestMatcher requestMatcher, boolean postOnly) {
		super(requestMatcher);
		this.postOnly = postOnly;
	}

	private TOKEN getToken(HttpServletRequest request) throws InstantiationException, IllegalAccessException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		REQ body = null;
		try {
			body = (REQ)JsonUtil.from(request.getInputStream(),LoginReqVo.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (body == null) {
			throw new BadCredentialsException("请求参数不合法");
		}

		return body.toAuthentication();
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
}
