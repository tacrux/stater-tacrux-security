package pro.tacrux.security.core.service.AuthorityValidators;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AccessValidator {
    /**
     * 权限校验逻辑
     *
     * @param request
     * @param authentication
     * @return
     */
    boolean validate(HttpServletRequest request, Authentication authentication);


}
