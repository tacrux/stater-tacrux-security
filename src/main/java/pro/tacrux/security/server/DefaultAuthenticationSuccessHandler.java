package pro.tacrux.security.server;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pro.tacrux.security.core.context.OnlineUserHolder;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.model.R;
import pro.tacrux.security.util.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/16 16:09
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/16 16:09    wangtao@360humi.com     new file.
 * </pre>
 */
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser)authentication.getDetails();

        String jwt = JwtUtils.create(7 * 24 * 60, loginUser.getUsername(), loginUser.getStoreId());

        R.ok(loginUser).writeTo(response.getOutputStream());
    }
}
