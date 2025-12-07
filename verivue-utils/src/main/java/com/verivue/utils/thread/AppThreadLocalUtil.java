package com.verivue.utils.thread;


import com.verivue.model.user.pojo.ApUser;

public class AppThreadLocalUtil {

    private final static ThreadLocal<ApUser> AP_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * Add User
     * @param apUser
     */
    public static void  setUser(ApUser apUser){
        AP_USER_THREAD_LOCAL.set(apUser);
    }

    /**
     * Get User
     */
    public static ApUser getUser(){
        return AP_USER_THREAD_LOCAL.get();
    }

    /**
     * Remove User
     */
    public static void removeUser(){
        AP_USER_THREAD_LOCAL.remove();
    }
}