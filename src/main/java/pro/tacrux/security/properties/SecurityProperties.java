package pro.tacrux.security.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import pro.tacrux.security.constants.Constants;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * @author tacurx
 */
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
public class SecurityProperties {

    public static final String PREFIX = Constants.CommonConstant.PROJECT_NAME_PREFIX + "." + "security";

    /**
     * 认证授权服务端配置,客户端配置无效
     */
    private SecurityServer server = new SecurityServer();

    /**
     * 认证授权客户端配置,服务端同时也是访问受体，作为客户端，可同时配置
     */

    private SecurityClient client = new SecurityClient();

    /**
     * Redis配置
     */
    private SecurityRedisProperties redis = new SecurityRedisProperties();

    @Data
    @Accessors(chain = true)
    public static class SecurityServer {


        /**
         * 认证登录页面
         */
        private String loginPage = "/login";

        /**
         * 登录提交地址.
         */
        private String loginProcessingUrl = "/authentication/form";

        /**
         * 会话有效时长：单位秒, 默认30分钟(静默30分钟后将失效);
         */
        private int sessionValiditySeconds = 30 * 60;

        /**
         * 授权令牌有效时长：单位秒;若未配置时，默认使用授权客户端的有效时长.
         */
        private Integer tokenValiditySeconds;


        /**
         * 服务端用户认证成功后信息缓存模式, 默认Redis.
         */
        private AuthenticationCacheModel cacheModel = AuthenticationCacheModel.REDIS;


    }

    /**
     * <pre>
     * <b>认证授权客户端配置.</b>
     * <b>Description:</b>
     *  服务端同时也是访问受体，作为客户端，可同时配置
     *
     * <b>Author:</b> tacrux
     * <b>Date:</b> 2022年4月30日 下午10:26:00
     * <b>Copyright:</b> Copyright tacrux All rights reserved.
     * <b>Changelog:</b>
     *   Ver   		Date                    Author               	 Detail
     *   ----------------------------------------------------------------------
     *   1.0   2022年4月30日 下午10:26:00    tacrux     new file.
     * </pre>
     */
    @Data
    @Accessors(chain = true)
    public static class SecurityClient {

        /**
         * 当前系统编码：和创建的子系统编码一致，否则会无权限
         */
        private String systemCode;

        /**
         * 安全组件启动模式
         */
        private SecurityModel model = SecurityModel.CLIENT;

        /**
         * 公开访问的Url列表
         */
        private List<String> publicAccessUrls = Collections.emptyList();

    }

    /**
     * 项目加载安全组件的身份
     */
    public enum SecurityModel {
        /**
         * 服务端
         */
        SERVER,
        /**
         * 客户端
         */
        CLIENT,
    }

    /**
     * principal的存储方式
     */
    public enum AuthenticationCacheModel {
        /**
         * 本地
         */
        LOCAL,
        /**
         * redis
         */
        REDIS
    }

    @Data
    @Accessors(chain = true)
    public static class SecurityRedisProperties {

        /**
         * Database index used by the connection factory.
         */
        private Integer database;

        /**
         * Connection URL. Overrides host, port, and password. User is ignored. Example:
         * redis://user:password@example.com:6379
         */
        private String url;

        /**
         * Redis server host.
         */
        private String host;

        /**
         * Login password of the redis server.
         */
        private String password;

        /**
         * Redis server port.
         */
        private Integer port;

        /**
         * Whether to enable SSL support.
         */
        private boolean ssl;

        /**
         * Connection timeout.
         */
        private Duration timeout;

        private Sentinel sentinel;

        private Cluster cluster;


        /**
         * Cluster properties.
         */
        @Data
        @Accessors(chain = true)
        public static class Cluster {

            /**
             * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
             * "initial" list of cluster nodes and is required to have at least one entry.
             */
            private List<String> nodes;

            /**
             * Maximum number of redirects to follow when executing commands across the
             * cluster.
             */
            private Integer maxRedirects;

        }

        /**
         * Redis sentinel properties.
         */
        @Data
        @Accessors(chain = true)
        public static class Sentinel {

            /**
             * Name of the Redis server.
             */
            private String master;

            /**
             * Comma-separated list of "host:port" pairs.
             */
            private List<String> nodes;

        }


    }


}



