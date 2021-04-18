package xyz.thetbw.none.system

import io.ktor.routing.*
import mu.KotlinLogging
import xyz.thetbw.none.system.controller.api.systemLoginApi
import xyz.thetbw.none.system.controller.api.systemUserApi

private val logger = KotlinLogging.logger {  }

/**
 * 注册路由
 */
fun Routing.systemRouting(){
    logger.info { "开始注册系统路由" }

    this.systemLoginApi() //登录注册相关接口
    route("/admin"){
        systemUserApi() //用户管理相关接口
    }
}