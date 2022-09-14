/**
 * 
 */
package pro.tacrux.security.server.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.dao.AbstractUserDetailsService;
import pro.tacrux.security.server.model.LoginReqVo;
import pro.tacrux.security.server.token.AuthenticationToken;

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
public interface AuthenticationProvider<TOKEN extends AuthenticationToken, SVC extends AbstractUserDetailsService> extends org.springframework.security.authentication.AuthenticationProvider {
	TOKEN authenticate(TOKEN authentication,SVC userDetailService) throws AuthenticationException;
}
