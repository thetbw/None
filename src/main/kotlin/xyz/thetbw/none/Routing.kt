package xyz.thetbw.none

import io.ktor.application.*

import io.ktor.routing.*
import mu.KotlinLogging
import xyz.thetbw.none.system.systemRouting

private val logger = KotlinLogging.logger {  }
/**
 * 全局路由
 */
fun Application.routing(){

    logger.info { "开始注册路由" }

    install(Routing){
        systemRouting() //系统路由
    }


}