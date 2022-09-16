package pro.tacrux.security.core.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import pro.tacrux.security.model.R;
import pro.tacrux.security.core.context.OnlineUserHolder;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.util.JsonUtil;
import pro.tacrux.security.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/5 22:09
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/5 22:09    tacrux     new file.
 * </pre>
 */
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OnlineUserHolder onlineUserHolder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try {
            // 从header或参数列表中获取token
            String tokenParameterName = HttpHeaders.AUTHORIZATION;
            token = StringUtils.defaultString(request.getParameter("accessToken"), request.getHeader(tokenParameterName));
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JsonUtil.to(R.fail(R.Status.UNAUTHORIZED)));
            return;
        }

        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 移除Bearer Token认证所带的前缀，兼容使用Authorization授权
        token = token.replaceAll("Bearer ", "");

        // 校验解码jwt
        DecodedJWT decodedjwt = null;
        try {
            decodedjwt = JwtUtils.decode(token);
            Assert.notNull(decodedjwt,"");
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(R.fail()));
            return;
        }


        //token有效期校验
        if (decodedjwt.getExpiresAt().after(new Date())) {
            // TODO: 2022/9/6 发送事件

            // TODO: 2022/9/6 删除session
            request.getSession().invalidate();

            // 返回响应消息
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(R.fail(R.Status.UNAUTHORIZED)));
            return;
        }

        // 设置认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(decodedjwt));

        filterChain.doFilter(request, response);
        return;
    }


    public Authentication getAuthentication(DecodedJWT decodedjwt) {
        LoginUser loginUser = onlineUserHolder.load(decodedjwt.getId());
        return new UsernamePasswordAuthenticationToken(loginUser.getUsername(), decodedjwt.getToken(), loginUser.getAuthorities());
    }
}

