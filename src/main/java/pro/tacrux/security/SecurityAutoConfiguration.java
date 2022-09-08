package pro.tacrux.security;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pro.tacrux.security.config.SecurityConfig;
import pro.tacrux.security.properties.SecurityProperties;

/**
 * @author tacrux
 */
@Configuration
@EnableConfigurationProperties({
        SecurityProperties.class})
@ImportAutoConfiguration(
        SecurityConfig.class
)
public class SecurityAutoConfiguration {

}
