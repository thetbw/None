package xyz.thetbw.none.common

import io.ktor.application.*

/**
 *  全局 Application
 */
object ApplicationHolder {

    private lateinit var application: Application;

    /**
     * 初始化Application
     */
    fun init(application: Application){
        if (this::application.isInitialized){
            throw NullPointerException("变量已经初始化了");
        }
        ApplicationHolder.application = application;
    }

    /**
     * 获取Application
     */
    fun getApplication() :Application{
        return application;
    }
}