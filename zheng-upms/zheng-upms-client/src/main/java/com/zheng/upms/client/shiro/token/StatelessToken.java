package com.zheng.upms.client.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-06-29 10:19
 **/
public class StatelessToken implements AuthenticationToken{
    private static final long serialVersionUID = 1L;
    private String username;  //用户名
   // private Map<String, ?> params;  //请求参数
    private String password;  //摘要
    //省略部分代码
    public StatelessToken(String username,
                          String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public Object getPrincipal() {  return username;}

    public Object getCredentials() {  return password;}

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
