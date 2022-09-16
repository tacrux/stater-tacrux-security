package pro.tacrux.security.demo.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.model.UriGrantedAuthority;
import pro.tacrux.security.server.dao.AbstractUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> wangtao@360humi.com
 * <b>Date:</b> 2022/9/14 17:24
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/14 17:24    wangtao@360humi.com     new file.
 * </pre>
 */
//@Service
public class UserPasswordUserDetailService extends AbstractUserDetailsService<UsernamePasswordReqVo> {

    private static List<DemoUser> demoUsers = new ArrayList<>();
    private static final UriGrantedAuthority query = new UriGrantedAuthority(RequestMethod.GET,"/demo/query","demo");
    private static final UriGrantedAuthority delete = new UriGrantedAuthority(RequestMethod.GET,"/demo/delete","demo");
    private static final UriGrantedAuthority save = new UriGrantedAuthority(RequestMethod.GET,"/demo/save","demo");

    static {
        demoUsers.add(new DemoUser("1", "u1", "p1", "m1", "e1", true, Sets.newHashSet(query)));
        demoUsers.add(new DemoUser("2", "u2", "p2", "m2", "e2", true,Sets.newHashSet(query,save)));
    }

    public UserPasswordUserDetailService() {
    }

    @Override
    public LoginUser loadUserDetails(Authentication authentication, UsernamePasswordReqVo parameter,
        HttpServletRequest request, HttpServletResponse response) {
        DemoUser user = demoUsers.stream().filter(
                u -> (u.getUserid().equals(parameter.getUsername())) || (u.getPhone().equals(parameter.getUsername()))
                    || (u.getUsername().equals(parameter.getUsername())) || (u.getEmail().equals(parameter.getUsername())))
            .findFirst().orElseThrow(() -> new UsernameNotFoundException("未找到用户"));

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user,loginUser);
        return loginUser;
    }

    @Override
    public boolean verification(LoginUser loginUser, UsernamePasswordReqVo parameter, HttpServletRequest request,
        HttpServletResponse response) {
        return false;
    }

    @AllArgsConstructor
    @Data
    public static class DemoUser {
        private String userid;
        private String username;
        private String password;
        private String phone;
        private String email;
        private Boolean enabled;
        private Collection<UriGrantedAuthority> authorities;
    }
}
