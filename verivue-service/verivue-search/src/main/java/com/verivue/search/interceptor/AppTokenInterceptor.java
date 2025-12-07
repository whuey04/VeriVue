package com.verivue.search.interceptor;

import com.verivue.model.user.pojo.ApUser;
import com.verivue.utils.thread.AppThreadLocalUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String userId = request.getHeader("userId");
            if (userId != null) {
                ApUser apUser = new ApUser();
                apUser.setId(Long.valueOf(userId));
                AppThreadLocalUtil.setUser(apUser);
            }
            return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            AppThreadLocalUtil.removeUser();
    }
}
