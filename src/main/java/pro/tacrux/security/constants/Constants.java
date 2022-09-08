package pro.tacrux.security.constants;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/7 9:59
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/7 9:59    tacrux     new file.
 * </pre>
 */
public class Constants {

    /**
     * 公共常量
     *
     * @author Wangtao
     * @author tacrux
     * @date 2022/9/2 16:11
     */
    public interface CommonConstant {
        String PROJECT_NAME_PREFIX = "tacrux";

        /** 认证返回类型参数名称. */
        String OAUTH_RETURN_TYPE_KEY 				= "returnType";
    }

    public interface StoreKeys {
        String SPRING_SESSION_LOGIN_USER_KEY = "loginUser";
    }

}
