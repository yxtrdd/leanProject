package cn.easyproject.easyee.sm.sys.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;

import cn.easyproject.easyee.sm.sys.entity.SysUser;
import cn.easyproject.easyshiro.EasyAuthenticationInterceptor;

/**
 * Shiro 认证拦截器
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 2.1.0
 */
public class AuthenticationInterceptor implements EasyAuthenticationInterceptor {

    @Override
    public void afterSuccess(ServletRequest request, ServletResponse response, AuthenticationToken token) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        // 不要强制转换，防止 devtools 的 RestartClassLoader 导致的 cast exception
        UsernamePasswordEncodeToken downToken = new UsernamePasswordEncodeToken();
        downToken.setUserId(Integer.valueOf(token.getClass().getMethod("getUserId").invoke(token).toString()));
        downToken.setName(token.getClass().getMethod("getName").invoke(token).toString());
        downToken.setPassword((char[]) token.getClass().getMethod("getPassword").invoke(token));
        downToken.setRealName(token.getClass().getMethod("getRealName").invoke(token).toString());
        downToken.setStatus(Integer.valueOf(token.getClass().getMethod("getStatus").invoke(token).toString()));

        // 用户锁定
        if (downToken.getStatus() == SysUser.STATUS_LOCK) {
            subject.logout();
            throw new LockedAccountException("账户已锁定！");
        }

        // 存入用户信息到Session
        SysUser sysUser = new SysUser(downToken.getName(), "");
        sysUser.setPassword(new String(downToken.getPassword()));
        sysUser.setRealName(downToken.getRealName());
        sysUser.setStatus(downToken.getStatus());
        sysUser.setUserId(downToken.getUserId());
        subject.getSession().setAttribute("USER", sysUser);

        // 初始化菜单列表和操作权限
        RefreshPermission.initOnShiro(subject.getSession(), downToken);

    }

    @Override
    public void afterFailure(ServletRequest request, ServletResponse response, AuthenticationToken token, Exception e) throws Exception {

    }

}
