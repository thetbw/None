package xyz.thetbw.none.plugin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.freemarker.*

fun  Application.freemarker(){

    /** freemarker配置 **/
    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

}