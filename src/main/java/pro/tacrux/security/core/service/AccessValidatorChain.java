package pro.tacrux.security.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import pro.tacrux.security.core.annotation.CustomAccess;
import pro.tacrux.security.core.service.AuthorityValidators.AbstractPreAccessValidator;
import pro.tacrux.security.core.service.AuthorityValidators.AccessValidator;
import pro.tacrux.security.core.context.AnnotationAccessHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * <b>校验认证权限过滤.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2019年1月3日 下午4:00:18
 * <b>Copyright:</b> Copyright 2022 tacrux. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2019年1月3日 下午4:00:18    tacrux     new file.
 * </pre>
 *
 * @author tacrux
 */
@Slf4j
public class AccessValidatorChain {

    /**
     * 权限过滤器.
     */
    private List<AccessValidator> accessValidators;

    /**
     * <pre>
     * 	无参构造，初始化权限过滤器
     * </pre>
     */
    public AccessValidatorChain(List<AccessValidator> accessValidators) {
        this.accessValidators = accessValidators;
    }

    /**
     * <pre>
     * 	权限校验,按照支持的过滤器检查访问权限
     * </pre>
     */
    public boolean validate(HttpServletRequest request, Authentication authentication) {

        Annotation annotation = AnnotationAccessHolder.getAccessAnnotation(request);

        // 自定义注解，使用注解指定校验器
        if (annotation instanceof CustomAccess) {
            return customValidate(request, authentication, (CustomAccess)annotation);
        }

        return preValidate(request, authentication, annotation);

    }

    /**
     * 调用自定义校验器
     */
    private boolean customValidate(HttpServletRequest request, Authentication authentication, CustomAccess annotation) {
        boolean result;
        Class<? extends AccessValidator> customValidatorClazz = annotation.validator();

        //获取自定义校验器bean
        AccessValidator accessValidator = accessValidators.stream()
            .filter(validator -> validator.getClass().isAssignableFrom(customValidatorClazz)).findAny()
            .orElseThrow(() -> new AccessDeniedException("未找到" + customValidatorClazz.getName() + "的bean"));
        //调用校验方法
        try {
            Method validate =
                customValidatorClazz.getMethod("validate", HttpServletRequest.class, Authentication.class);
            result = (Boolean)validate.invoke(accessValidator, request, authentication);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new AccessDeniedException("自定义权限校验失败");
        }
        return result;
    }

    /**
     * 调用预制校验器
     */
    private boolean preValidate(HttpServletRequest request, Authentication authentication, Annotation annotation) {
        return accessValidators.stream().filter(a -> a instanceof AbstractPreAccessValidator)
            .map(a -> (AbstractPreAccessValidator)(a)).filter(a -> a.isSupport(annotation))
            .anyMatch(a -> a.validate(request, authentication));
    }

}

