package com.verivue.wemedia.interceptor;

import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.utils.thread.WmThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class WmTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get the data in header
        String userId = request.getHeader("userId");
        Optional<String> optional = Optional.ofNullable(userId);
        if (optional.isPresent()) {
            // Save userId into threadLocal
            WmUser wmUser = new WmUser();
            wmUser.setId(Long.valueOf(userId));
            WmThreadLocalUtil.setUser(wmUser);
            log.info("WmTokenFilter: set user data into threadlocal...");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        log.info("WmTokenFilter: Clean threadlocal...");
        WmThreadLocalUtil.clear();
    }
}
