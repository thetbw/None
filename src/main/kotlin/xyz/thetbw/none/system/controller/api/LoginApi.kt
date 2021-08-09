package xyz.thetbw.none.system.controller.api

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.kodein.di.instance
import xyz.thetbw.none.di
import xyz.thetbw.none.plugin.respondObj
import xyz.thetbw.none.system.data.LoginUser
import xyz.thetbw.none.system.data.domain.SysUser
import xyz.thetbw.none.system.service.TokenService

fun Route.systemLoginApi(){

    val tokenService: TokenService by di.instance()

    route("/login"){
        get {
            val user = SysUser()
            user.name = "asas"
            user.nickName = "as"
            user.password = "asas"
            user.role = "admin"
            user.gender = 1
            user.email = "asasa"
            call.respondObj(tokenService.createToken(user))
//            call.respond(FreeMarkerContent("index.ftl", mapOf("as" to "asa")))
        }
    }
    route("/info"){
        get{
            val user = call.principal<LoginUser>()
            call.respondObj(user)
        }
    }



}