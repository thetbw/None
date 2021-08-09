package xyz.thetbw.none.plugin

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import xyz.thetbw.none.system.systemRouting

/**
 * 全局路由
 */
fun Application.routing(){

    install(Routing){
        authenticate(optional = true) {
            route("/api"){
                systemRouting() //系统路由
            }
        }
    }
}