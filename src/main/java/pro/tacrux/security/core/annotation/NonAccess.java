/**
 * 
 */
package pro.tacrux.security.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <pre>
 * <b>无需权限访问.</b>
 * <b>Description:</b> 
 *    
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2019年1月7日 上午9:19:40
 * <b>Copyright:</b> Copyright 2022 tacrux. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2019年1月7日 上午9:19:40    tacrux     new file.
 * </pre>
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface NonAccess {
}
