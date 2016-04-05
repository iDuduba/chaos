package wang.laic.chaos.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wang.laic.chaos.model.User;
import wang.laic.chaos.service.RoleService;
import wang.laic.chaos.service.UserService;

public class ShiroRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    
    /**
     * Shiro权限认证
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("################## 执行Shiro权限认证 ##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String username = (String)super.getAvailablePrincipal(principals); 
        
        //到数据库查是否有此对象
        User user=userService.findUserByLoginName(username);
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if(user != null && user.getStatus() == 0){
        	//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            
            /*
            //用户的角色集合
            info.setRoles(user.getRolesName());
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<Role> roleList=user.getRoleList();
            for (Role role : roleList) {
                info.addStringPermissions(role.getPermissionsName());
            }
            */
            
            
            //或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色    
            info.addRole("admin");  
            //添加权限  
            info.addStringPermission("admin:manage");  
            logger.info("已为用户[{}]赋予了[admin]角色和[admin:manage]权限", username);
            
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * Shiro登录认证：
     * 用户提交 用户名和密码
     * shiro 封装令牌
     * realm 通过用户名将密码查询返回
     * shiro 自动去比较查询出密码和用户输入密码是否一致
     * 进行登陆控制 )
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("################## 执行Shiro登录认证 ##################");
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findUserByLoginName(token.getUsername());
		
        // 账号不存在或未启用
        if (user == null || user.getStatus() == 1) {
            return null;
        } else {
        	// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        	return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        }
        
//        List<Long> roleList = roleService.findRoleIdListByUserId(user.getId());
//        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginname(), user.getName(), roleList);
//        // 认证缓存信息
//        return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());
	}

}
