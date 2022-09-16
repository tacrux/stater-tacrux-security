package pro.tacrux.security.server.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/13 16:12
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/13 16:12    wangtao@360humi.com     new file.
 * </pre>
 */
@Data
public class CredentialsVo implements Serializable {
    private String principal;
}
