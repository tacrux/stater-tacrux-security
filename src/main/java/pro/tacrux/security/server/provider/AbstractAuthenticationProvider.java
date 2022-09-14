/**
 *
 */
package pro.tacrux.security.server.provider;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pro.tacrux.security.server.dao.AbstractUserDetailsService;
import pro.tacrux.security.server.model.LoginReqVo;
import pro.tacrux.security.server.token.AuthenticationToken;

/**
 * 抽象用户提供者
 * @param <TOKEN> AuthenticationToken
 * @param <SVC> UserDetailService
 */
@Slf4j
public abstract class AbstractAuthenticationProvider<TOKEN extends AuthenticationToken, SVC extends AbstractUserDetailsService>
    implements AuthenticationProvider<TOKEN, SVC> {

    abstract SVC getUserDetailsService();

    @Override
    public final TOKEN authenticate(Authentication authentication) throws AuthenticationException {
        SVC userDetailService = getUserDetailsService();
        return authenticate((TOKEN)authentication, userDetailService);
    }
}
