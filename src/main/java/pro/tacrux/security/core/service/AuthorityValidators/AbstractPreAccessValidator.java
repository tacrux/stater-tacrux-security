package pro.tacrux.security.core.service.AuthorityValidators;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/8 13:57
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/8 13:57    tacrux     new file.
 * </pre>
 */
public abstract class AbstractPreAccessValidator implements AccessValidator {

    /**
     * <pre>
     * 	是否支持
     * </pre>
     *
     * @param annotation
     * @return
     */
   public abstract boolean isSupport(Annotation annotation);
}
