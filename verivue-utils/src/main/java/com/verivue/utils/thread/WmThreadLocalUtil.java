package com.verivue.utils.thread;


import com.verivue.model.wemedia.pojo.WmUser;

public class WmThreadLocalUtil {

    private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * Add User
     * @param wmUser
     */
    public static void  setUser(WmUser wmUser){
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    /**
     * Get User
     */
    public static WmUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    /**
     * Remove User
     */
    public static void clear(){
        WM_USER_THREAD_LOCAL.remove();
    }
}