/**
 * 
 */
package pro.tacrux.security.server.token;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.model.CredentialsVo;

import java.util.Collections;

/**
 * <pre>
 * <b>认证授权请求令牌.</b>
 * <b>Description:</b> 
 *  
 * 
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022年5月6日 下午3:12:00
 * <b>Copyright:</b> Copyright tacrux All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022年5月6日 下午3:12:00    tacrux     new file.
 * </pre>
 */
@Accessors(chain = true)
public class AuthenticationToken<C extends CredentialsVo> extends AbstractAuthenticationToken {
	


	@Setter
	@Getter
	private String principal;
	
	@Setter
	@Getter
	private C credentials;

	@Setter
	@Getter
	private LoginUser details;

	public AuthenticationToken(String principal, C credentials) {
		super(Collections.emptyList());
		this.principal = principal;
		this.credentials = credentials;
	}
}
