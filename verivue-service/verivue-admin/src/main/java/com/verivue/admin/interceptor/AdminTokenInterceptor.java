package com.verivue.admin.interceptor;

import com.verivue.model.admin.pojo.AdUser;
import com.verivue.utils.thread.AdminThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        if (userId != null) {
            AdUser adUser = new AdUser();
            adUser.setId(Long.valueOf(userId));
            AdminThreadLocalUtil.setUser(adUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AdminThreadLocalUtil.removeUser();
    }
}
