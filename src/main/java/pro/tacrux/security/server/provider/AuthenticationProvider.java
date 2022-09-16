/**
 *
 */
package pro.tacrux.security.server.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.dao.AbstractUserDetailsService;
import pro.tacrux.security.server.dao.UserDetailsService;
import pro.tacrux.security.server.model.CredentialsVo;
import pro.tacrux.security.server.token.AuthenticationToken;
import pro.tacrux.security.util.SpringContextUtils;

import java.util.List;

/**
 * <pre>
 * <b>认证授权提供者接口类.</b>
 * <b>Description:</b> 
 *
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022年5月5日 下午12:54:23
 * <b>Copyright:</b> Copyright tacrux All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022年5月5日 下午12:54:23    tacrux     new file.
 * </pre>
 */
public class AuthenticationProvider<C extends CredentialsVo> implements org.springframework.security.authentication.AuthenticationProvider {



	@Override
	public final AuthenticationToken<C> authenticate(Authentication authentication) throws AuthenticationException {
		if(authentication instanceof AuthenticationToken){
			// TODO: 2022/9/15 抛错
		}
		AuthenticationToken<C> authenticationToken = (AuthenticationToken<C>)authentication;
		LoginUser loginUser = SpringContextUtils.getBean(UserDetailsService.class).loadUserDetails(authenticationToken.getCredentials());
		if(loginUser==null){
			// TODO: 2022/9/15
		}

		//通过认证
		authenticationToken.setDetails(loginUser);
		authenticationToken.setAuthenticated(true);

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(AuthenticationToken.class);
	}

}
