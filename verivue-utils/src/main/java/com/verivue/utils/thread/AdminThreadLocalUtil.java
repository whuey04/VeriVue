package com.verivue.utils.thread;


import com.verivue.model.admin.pojo.AdUser;

public class AdminThreadLocalUtil {

    private final static ThreadLocal<AdUser> AD_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * Add User
     * @param adUser
     */
    public static void  setUser(AdUser adUser){
        AD_USER_THREAD_LOCAL.set(adUser);
    }

    /**
     * Get User
     */
    public static AdUser getUser(){
        return AD_USER_THREAD_LOCAL.get();
    }

    /**
     * Remove User
     */
    public static void removeUser(){
        AD_USER_THREAD_LOCAL.remove();
    }
}