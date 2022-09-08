/**
 * 
 */
package pro.tacrux.security.core.context;

import java.util.Collection;

import pro.tacrux.security.model.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * <b>在线用户持有器接口.</b>
 * <b>Description:</b> 
 * 	提供根据不同条件加载、修改、移除在线用户
 * 
 * <b>Author:</b> tacrux
 * <b>Date:</b> 2021年8月31日 上午10:10:23
 * <b>Copyright:</b> Copyright 2017-2021 www.360humi.com Technology Co., Ltd. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   		Date                    Author               	 Detail
 *   ----------------------------------------------------------------------
 *   1.0   2021年8月31日 上午10:10:23    tacrux     new file.
 * </pre>
 */
public interface OnlineUserHolder {
	
	/**
	 * <pre>
	 *	加载用户信息
	 * </pre>
	 * @return
	 */
	LoginUser load(String id);

	/**
	 * <pre>
	 *	加载用户信息
	 * </pre>
	 * @return
	 */
	LoginUser load(HttpServletRequest request);

	// TODO: 2022/9/7 spring session不好实现
//	/**
//	 * spring session不好实现
//	 */
//	List<LoginUser> findAll();
	
	/**
	 * 根据用户ID获取在线用户
	 * @return	由于一个用户可以多次登录，这里返回一个List
	 */
	Collection<LoginUser> loadByUsername(String username);

	
	/**
	 * 更新缓存中的用户信息
	 * @param loginUser
	 */
	boolean update(LoginUser loginUser);
	
	/**
	 * <pre>
	 *	解除已加载的用户信息.如果策略支持
	 * </pre>
	 * @param tokenId
	 */
	boolean remove(String tokenId);
	
}
