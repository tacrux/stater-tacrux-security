/**
 *
 */
package pro.tacrux.security.core.service.AuthorityValidators;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import pro.tacrux.security.core.annotation.RbacAccess;
import pro.tacrux.security.core.context.AnnotationAccessHolder;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.properties.SecurityProperties;
import pro.tacrux.security.util.LoginUserUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * <b>系统授权访问过滤器.</b>
 * <b>Description:</b> 
 *	根据系统配置授权 用户-角色-资源访问权限过滤
 *	---------------------   
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2019年11月12日 下午1:46:59
 * <b>Copyright:</b> Copyright 2022 tacrux. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2019年11月12日 下午1:46:59    tacrux     new file.
 * </pre>
 */
@Slf4j
@Data
public class RbacAccessValidator extends AbstractPreAccessValidator {

    @Autowired
    private AuthenticationTrustResolver trustResolver;
    @Autowired
    private SecurityProperties properties;

    @Override
    public boolean validate(HttpServletRequest request, Authentication authentication) {

        Object details = authentication.getDetails();

        // 匿名用户直接返回false
        if (trustResolver.isAnonymous(authentication) || !authentication.isAuthenticated()) {
            return false;
        }

        LoginUser loginUser = LoginUserUtil.get();

        // 如果缓存中不存在并且当前授权凭证类型为用户类型，直接取出用户使用
        if (loginUser == null && (details instanceof LoginUser)) {
            loginUser = (LoginUser)details;
        } else {
            return false;
        }

        // 获取当前系统用户的权限
        String systemCode = properties.getClient().getSystemCode();
        if (StringUtils.isBlank(systemCode)) {
            log.warn("当前未设置系统编码：${yunxi.security.client.system-code}");
            return false;
        }
        List<String> systemCodeSplits = Arrays.asList(systemCode.split(","));

        return loginUser.getAuthorities().stream()
            .filter(authority -> systemCodeSplits.contains(authority.getSystemCode()))
            .anyMatch(AnnotationAccessHolder.RequestSimpleMatchInfo.fromRequest(request)::match);
    }

    @Override
    public boolean isSupport(Annotation annotation) {
        return annotation instanceof RbacAccess;
    }

}
