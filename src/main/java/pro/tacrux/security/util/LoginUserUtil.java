package pro.tacrux.security.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import pro.tacrux.security.core.context.OnlineUserHolder;
import pro.tacrux.security.model.LoginUser;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/7 9:53
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/7 9:53    tacrux     new file.
 * </pre>
 */
public class LoginUserUtil {
    private static OnlineUserHolder onlineUserHolder;

    @Autowired
    public void setOnlineUserHolder(OnlineUserHolder onlineUserHolder) {
        LoginUserUtil.onlineUserHolder = onlineUserHolder;
    }

    public static LoginUser get(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes==null){
            return null;
        }
        String sessionId = requestAttributes.getSessionId();
        if(StringUtils.isBlank(sessionId)){
            return null;
        }
        return onlineUserHolder.load(sessionId);
    }

}
