package pro.tacrux.security.core.context;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import pro.tacrux.security.constants.Constants;
import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * <b></b>
 * <b>Description:</b>
 *
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022/9/7 9:50
 * <b>Copyright:</b> Copyright 2022 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022/9/7 9:50    tacrux     new file.
 * </pre>
 */
public class SpringSessionOnlineUserHolder implements OnlineUserHolder {

    @Autowired
    private FindByIndexNameSessionRepository<Session> sessionRepository;

    @Override
    public LoginUser load(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object loginUserObj = session.getAttribute(Constants.StoreKeys.SPRING_SESSION_LOGIN_USER_KEY);
        return JsonUtil.convert(loginUserObj, LoginUser.class);
    }

    @Override
    public LoginUser load(String storeId) {
        Session session = sessionRepository.findById(storeId);
        return LoginUser.fromSpringSession(session);
    }

    @Override
    public Collection<LoginUser> loadByUsername(String username) {
        Map<String, ? extends Session> sessionMap = sessionRepository.findByPrincipalName(username);
        if(MapUtils.isEmpty(sessionMap)){
            return Collections.emptySet();
        }
        return sessionMap.values().stream().map(LoginUser::fromSpringSession).collect(Collectors.toSet());
    }

    @Override
    public boolean update(LoginUser loginUser) {
        if(loginUser==null|| StringUtils.isBlank(loginUser.getStoreId())){
            return false;
        }
        Session session = sessionRepository.findById(loginUser.getStoreId());
        session.setAttribute(Constants.StoreKeys.SPRING_SESSION_LOGIN_USER_KEY,loginUser);
        sessionRepository.save(session);
        return true;
    }

    @Override
    public boolean remove(String storeId) {
        sessionRepository.deleteById(storeId);
        return true;
    }
}
