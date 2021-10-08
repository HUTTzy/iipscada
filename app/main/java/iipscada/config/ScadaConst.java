package iipscada.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IIPScada常量
 *
 * @auther :mao
 * @date :18-10-18 11:12
 */
public interface ScadaConst {
    String USER = "scada_user";
    int SUPER_ADMIN = 1;
    //角色权限
    Map<Long, Set<String>> ROLE_AUTH = new ConcurrentHashMap<>();
    //系统配置
    Map<String, String> SYS_CONFIG = new HashMap<>();
    //分享出的用户
    String SHARE_ID = "shareId";
}

