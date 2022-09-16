package pro.tacrux.security.demo.security;

import lombok.Data;
import pro.tacrux.security.server.model.CredentialsVo;

import java.util.Collections;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/14 17:22
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/14 17:22    wangtao@360humi.com     new file.
 * </pre>
 */
@Data
public class UsernamePasswordReqVo extends CredentialsVo {
    private String username;
    private String password;
    @Override
    public String getPrincipal() {
        return username;
    }
}
