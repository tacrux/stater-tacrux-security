/**
 * 
 */
package pro.tacrux.security.server.token;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.model.LoginReqVo;

import java.util.Collection;

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
public abstract class AuthenticationToken extends AbstractAuthenticationToken {
	


	@Setter
	@Getter
	private Object principal;
	
	@Setter
	@Getter
	private Object credentials;

	/**
	 * Creates a token with the supplied array of authorities.
	 *
	 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
	 *                    represented by this authentication object.
	 */
	public AuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal,
		Object credentials) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
	}
}
