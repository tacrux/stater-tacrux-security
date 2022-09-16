package pro.tacrux.security.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.tacrux.security.demo.security.UserPasswordUserDetailService;
import pro.tacrux.security.server.dao.AbstractUserDetailsService;
import pro.tacrux.security.server.dao.UserDetailsService;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/16 15:42
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/16 15:42    wangtao@360humi.com     new file.
 * </pre>
 */
@Configuration
public class UserDetailServiceBean {
    @Bean
    UserDetailsService userDetailsService(){
        return new UserPasswordUserDetailService();
    }
}
