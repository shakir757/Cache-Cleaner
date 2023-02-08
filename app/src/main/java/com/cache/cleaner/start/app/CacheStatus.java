package com.cache.cleaner.start.app;

import com.cache.cleaner.start.app.fragments.CacheCategoriesFragment;
import com.cache.cleaner.start.app.fragments.CacheFragment;

public class CacheStatus{
    Boolean cache_status = false;
    String app_function = "NONE";
    static CacheStatus instance = null;

    public void set_false(){
        cache_status = false;
    }

    public void set_true(){
        cache_status = true;
    }

    public void set_function(String function){
        app_function = function;
    }

    public Boolean get_cache_status(){
        return cache_status;
    }

    public String get_function(){
        return app_function;
    }

    public static CacheStatus getInstance(){
        if(CacheStatus.instance == null){
            CacheStatus.instance = new CacheStatus();
        }
        return CacheStatus.instance;
    }

    private CacheStatus(){}
}


