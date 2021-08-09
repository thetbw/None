package xyz.thetbw.none.system

import io.ktor.auth.*
import io.ktor.routing.*
import xyz.thetbw.none.common.SysUserRole
import xyz.thetbw.none.plugin.withRole
import xyz.thetbw.none.system.controller.api.systemLoginApi
import xyz.thetbw.none.system.controller.api.systemUserApi


/**
 * 注册路由
 */
fun Route.systemRouting(){

    this.systemLoginApi() //登录注册相关接口

    withRole(SysUserRole.ADMIN) {
        route("/admin"){
            systemUserApi() //用户管理相关接口
        }

    }



}