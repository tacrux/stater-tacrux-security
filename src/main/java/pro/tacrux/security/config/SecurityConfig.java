package pro.tacrux.security.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pro.tacrux.security.model.R;
import pro.tacrux.security.exception.JsonResponseAuthenticationEntryPoint;
import pro.tacrux.security.exception.JwtAccessDeinedHandler;
import pro.tacrux.security.core.service.AccessValidatorChain;
import pro.tacrux.security.core.service.AuthorityValidators.AccessValidator;
import pro.tacrux.security.core.filter.JWTAuthorizationFilter;
import pro.tacrux.security.properties.SecurityProperties;

import java.util.List;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/5 21:24
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/5 21:24    tacrux     new file.
 * </pre>
 *
 * @author tacrux
 */
@EnableWebSecurity
public class SecurityConfig {
    // @formatter:off
    /**
     * 静态资源
     */
    private static final String[] STATIC_RESOURCE_MATCHS =
        {"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/actuator/**", "/health/*", "/ws/*", "*.wsdl"};
    // @formatter:on

    @Autowired
    private SecurityProperties properties;

    /**
     * 加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注解式配置权限校验责任链
     * @param accessValidators 校验器
     */
    @Bean
    public AccessValidatorChain accessValidatorChain(@Autowired List<AccessValidator> accessValidators){
        return new AccessValidatorChain(accessValidators);
    }

    /**
     * 用户可信解析器
     */
    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver(){
        return new AuthenticationTrustResolverImpl();
    }

    /**
     * 直接在过滤器链里面配置httpSecurity
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // @formatter:off
        String[] permitPattern = ArrayUtils.addAll(STATIC_RESOURCE_MATCHS, properties.getClient().getPublicAccessUrls().toArray(new String[0]));

        return http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                // 允许跨域
                .cors()
                .and()
                // 配置是否需要认证
                .authorizeRequests()
                    // 静态资源和指定接口无需认证鉴权
                    .mvcMatchers(permitPattern).permitAll()
                    // 鉴权Filter
                    .anyRequest().access("@AccessValidatorChain.validate(request, authentication)")
                // 对于登录接口 允许匿名访问
                .and()
                //jwt入口
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                //错误处理
                .exceptionHandling().accessDeniedHandler(new JwtAccessDeinedHandler(null, HttpStatus.FORBIDDEN.value(), R.fail(R.Status.FORBIDDEN)))
                                    .authenticationEntryPoint(new JsonResponseAuthenticationEntryPoint(HttpStatus.UNAUTHORIZED.value(), R.fail(R.Status.UNAUTHORIZED)))
                .and()
                .build();
        // @formatter:on

    }

}
