/**
 * 
 */
package pro.tacrux.security.server.dao;

import pro.tacrux.security.model.LoginUser;
import pro.tacrux.security.server.model.CredentialsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * <b>认证授权加载用户详情服务接口.</b>
 * <b>Description:</b> 
 *  
 * 
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2022年5月5日 下午1:18:15
 * <b>Copyright:</b> Copyright tacrux All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2022年5月5日 下午1:18:15    tacrux     new file.
 * </pre>
 */
public interface UserDetailsService<C extends CredentialsVo>{

	/**
	 * 加载用户详情
	 * @param credentialsVo	认证请求参数
	 * @return
	 */
	 LoginUser loadUserDetails(C credentialsVo);

	boolean verification(LoginUser loginUser, C credentialsVo, HttpServletRequest request, HttpServletResponse response);


	/**
	 * 用户认证授权通过后
	 * @param loginUser	登录用户信息
	 * @param request
	 * @param response
	 */
	void afterVerification(LoginUser loginUser, C credentialsVo, HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * 用户认证授权通过后
	 * @param loginUser	登录用户信息
	 * @param request
	 * @param response
	 */
	 void beforeVerification(LoginUser loginUser, C credentialsVo, HttpServletRequest request, HttpServletResponse response);
}
