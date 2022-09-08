/**
 * 
 */
package pro.tacrux.security.core.service.AuthorityValidators;

import pro.tacrux.security.core.annotation.NonAccess;
import org.springframework.security.core.Authentication;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * <pre>
 * <b>无需权限过滤器.</b>
 * <b>Description:</b> 
 *	无需权限，匹配@NoAccess,默认返回true
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
public class NoAccessValidator extends AbstractPreAccessValidator {


	@Override
	public boolean validate(HttpServletRequest request, Authentication authentication) {
		return true;
	}

	@Override
	public boolean isSupport(@Nonnull Annotation annotation) {
		return annotation instanceof NonAccess;
	}

}
