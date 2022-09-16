package pro.tacrux.security.demo.security;

import org.springframework.stereotype.Component;
import pro.tacrux.security.server.filter.AbstractAuthenticationFilter;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/14 17:23
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/14 17:23    wangtao@360humi.com     new file.
 * </pre>
 */
@Component
public class UsernamePasswordFilter extends AbstractAuthenticationFilter<UsernamePasswordReqVo> {

    protected UsernamePasswordFilter() {
        super("/login/pwd", "POST");
    }

}
