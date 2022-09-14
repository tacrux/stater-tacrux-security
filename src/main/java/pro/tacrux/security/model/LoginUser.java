package pro.tacrux.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.Session;
import pro.tacrux.security.constants.Constants;
import pro.tacrux.security.util.JsonUtil;

import java.util.Collection;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/2 16:43
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/2 16:43    tacrux     new file.
 * </pre>
 * @author tacrux
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private String userid;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Boolean enabled;
    private Collection<UriGrantedAuthority> authorities;

    private String storeId;


    @Override
    public Collection<UriGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static LoginUser fromSpringSession(Session session){
        Object loginUserObj = session.getAttribute(Constants.StoreKeys.SPRING_SESSION_LOGIN_USER_KEY);
        return JsonUtil.convert(loginUserObj, LoginUser.class);
    }


}
