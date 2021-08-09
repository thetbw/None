package xyz.thetbw.none.system.controller.api

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import mu.KotlinLogging
import org.kodein.di.instance
import xyz.thetbw.none.di
import xyz.thetbw.none.excetion.AuthException
import xyz.thetbw.none.plugin.respondObj
import xyz.thetbw.none.system.service.UserService

private val logger  = KotlinLogging.logger {  }

fun Route.systemUserApi(){
    logger.info { "开始注册用户路由" }


    val userService : UserService by di.instance()


    route("/user"){
        get {

            call.respondObj("succes")
        }
        post {
            logger.info { "开始创建用户" }
//            userService.addUser()
        }

        delete {

        }

    }

}