package pro.tacrux.security.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pro.tacrux.security.core.filter.JWTAuthorizationFilter;
import pro.tacrux.security.core.service.AccessValidatorChain;
import pro.tacrux.security.core.service.AuthorityValidators.AccessValidator;
import pro.tacrux.security.exception.JsonResponseAuthenticationEntryPoint;
import pro.tacrux.security.exception.JwtAccessDeinedHandler;
import pro.tacrux.security.model.R;
import pro.tacrux.security.properties.SecurityProperties;
import pro.tacrux.security.server.DefaultAuthenticationSuccessHandler;
import pro.tacrux.security.server.dao.UserDetailsService;
import pro.tacrux.security.server.provider.AuthenticationProvider;

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
@Configuration
public class SecurityConfig {
    // @formatter:off
    /**
     * ????????????
     */
    private static final String[] STATIC_RESOURCE_MATCHS =
        {"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/actuator/**", "/health/*", "/ws/*", "*.wsdl"};
    // @formatter:on

    @Autowired
    private SecurityProperties properties;

    /**
     * ????????????
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() {
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword) || super.matches(rawPassword, encodedPassword);
            }
        };
    }

    /**
     * ????????????????????????????????????
     *
     * @param accessValidators ?????????
     */
    @Bean
    public AccessValidatorChain accessValidatorChain(@Autowired List<AccessValidator> accessValidators) {
        return new AccessValidatorChain(accessValidators);
    }

    /**
     * ?????????????????????
     */
    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
//
//    @Bean org.springframework.security.authentication.AuthenticationProvider authenticationProvider(){
//        return new AuthenticationProvider<>();
//    }


    @Bean
    AuthenticationManager authenticationManager(){
        return new ProviderManager(new AuthenticationProvider<>());
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new DefaultAuthenticationSuccessHandler();
    }

    /**
     * ?????????????????????????????????httpSecurity
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        String[] permitPattern = ArrayUtils.addAll(STATIC_RESOURCE_MATCHS, properties.getClient().getPublicAccessUrls().toArray(new String[0]));

        DefaultSecurityFilterChain build = http
            //??????csrf
            .csrf().disable()
            //?????????Session??????SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
            // ????????????
            .cors().and()
            // ????????????????????????
            .authorizeRequests(authorize -> authorize.antMatchers("/login/pwd","/error").permitAll()
                // ?????????????????????????????????????????????
//                .antMatchers(permitPattern).permitAll()
                // ??????Filter
                .anyRequest().authenticated())
//                .access("@AccessValidatorChain.validate(request, authentication)"))
            //jwt??????
            .addFilterAt(new JWTAuthorizationFilter(), BasicAuthenticationFilter.class)
            //????????????
            // @formatter:off
                .exceptionHandling().accessDeniedHandler(new JwtAccessDeinedHandler(null, HttpStatus.FORBIDDEN.value(), R.fail(R.Status.FORBIDDEN)))
                                    .authenticationEntryPoint(new JsonResponseAuthenticationEntryPoint(HttpStatus.UNAUTHORIZED.value(), R.fail(R.Status.UNAUTHORIZED)))
                .and()
                .build();
        return build;
        // @formatter:on

    }


}
