package com.zheng.upms.client.shiro.realm;

import com.zheng.common.util.RedisUtil;
import com.zheng.upms.client.shiro.token.StatelessToken;
import com.zheng.upms.dao.model.UpmsPermission;
import com.zheng.upms.dao.model.UpmsRole;
import com.zheng.upms.dao.model.UpmsUser;
import com.zheng.upms.rpc.api.UpmsApiService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhu on 2017/6/27.
 */
public class StateRealm extends AuthorizingRealm {
    @Autowired
    private UpmsApiService upmsApiService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    /**
     * 授权：验证权限时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        if (upmsUser == null) {
            throw new UnknownAccountException("无效用户");
        }
        // 当前用户所有角色
        List<UpmsRole> upmsRoles = upmsApiService.selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
        Set<String> roles = new HashSet<>();
        for (UpmsRole upmsRole : upmsRoles) {
            if (StringUtils.isNotBlank(upmsRole.getName())) {
                roles.add(upmsRole.getName());
            }
        }

        // 当前用户所有权限
        List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());
        Set<String> permissions = new HashSet<>();
        for (UpmsPermission upmsPermission : upmsPermissions) {
            if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                permissions.add(upmsPermission.getPermissionValue());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }
    /**
     * 认证：登录时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken)authenticationToken;
        String username = (String)statelessToken.getPrincipal();//不能为null,否则会报错的.
        String usernameToken = RedisUtil.get("username_token_"+username);
        // username = (String) authenticationToken.getPrincipal();
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(username, usernameToken, getName());
        return authInfo;
    }

    /**
     * {@inheritDoc}
     * 重写函数，如果不是web app登录，则用无状态授权
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        if (principals.fromRealm(getName()).isEmpty()) {
            return false;
        }
        else {
            return super.isPermitted(principals, permission);
        }
    }

    private String getPassword(String username) {//得到密钥，此处硬编码一个
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        return upmsUser.getPassword();
    }

}
