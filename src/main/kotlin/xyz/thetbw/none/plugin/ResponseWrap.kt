package xyz.thetbw.none.plugin

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.*
import io.ktor.utils.io.*
import kotlinx.coroutines.coroutineScope

fun Application.responseWrap(){
    install(AppResponseWrap)
    install(StatusPages)
}

class AppResponseWrap(configure: RoleBasedAuthorization.Configuration){
    private val monitorKey = AttributeKey<Long>("COST_TIME_MONITOR_START_TIME")

    class Configuration

    private fun interceptPipeline( pipeline: ApplicationCallPipeline){
        pipeline.intercept(ApplicationCallPipeline.Monitoring){
            val startTime = System.currentTimeMillis()
            call.attributes.put(monitorKey,startTime)
            try {
                coroutineScope{
                    proceed()
                }
            }catch (exception: Throwable){
                exception.printStack()
                val message = exception.message ?: "未知异常"
                val costTime = System.currentTimeMillis() - startTime
                val response = CommonResponse(false,message,costTime,null)
                call.respondObj(response,HttpStatusCode.Companion.InternalServerError)
            }

        }
        pipeline.sendPipeline.intercept(ApplicationSendPipeline.Transform){

            if (call.isResponseObj() && subject !is CommonResponse<*>){
                val startTime = call.attributes[monitorKey]
                val endTime = System.currentTimeMillis()
                val costTime = endTime - startTime
                val body = if(subject == RESPONSE_OBJECT_NULL) null else subject
                val response = CommonResponse(true,"success",costTime,body)
                proceedWith(response)
            }else if (subject is UnauthorizedResponse){
                val response = CommonResponse(false,"没有权限",0,null)
                call.response.status(io.ktor.http.HttpStatusCode.Companion.Unauthorized)
                call.response.responseType
                proceedWith(response)
            }else{
                proceedWith(subject)
            }
        }
    }

    companion object Feature:ApplicationFeature<ApplicationCallPipeline, RoleBasedAuthorization.Configuration, AppResponseWrap>{
        override val key =  AttributeKey<AppResponseWrap>("AppResponseWrap")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: RoleBasedAuthorization.Configuration.() -> Unit
        ): AppResponseWrap {
            val configuration = RoleBasedAuthorization.Configuration().apply(configure)
            return AppResponseWrap(configuration).apply { interceptPipeline(pipeline) }
        }

    }

}

val RESPONSE_OBJECT_KEY = AttributeKey<Int>("RESPONSE_OBJECT_KEY")

val RESPONSE_OBJECT_NULL  = Any()

/**
 * 返回一个对象，这个对象会被CommResponse封装
 */
suspend inline fun <reified T : Any> ApplicationCall.respondObj(message: T?, status: HttpStatusCode = HttpStatusCode.OK) {
    addResponseObjFlag()
    response.status(status)
    if (message == null){
        response.pipeline.execute(this,RESPONSE_OBJECT_NULL)
    }else {
        response.pipeline.execute(this,message as Any)
    }

}

/**
 * 添加一个标记，标识当前是返回一个对象
 */
fun ApplicationCall.addResponseObjFlag(){
    this.attributes.put(RESPONSE_OBJECT_KEY,1)
}

/**
 * 检查当前请求是否返回的是一个对象，如果返回的是一个对象，则会被CommResponse封装
 */
fun ApplicationCall.isResponseObj(): Boolean{
    return this.attributes.contains(RESPONSE_OBJECT_KEY)
}

data class CommonResponse<T>(
    /** 当前请求是否成功 */
    val success: Boolean,
    /** 当前请求msg */
    val msg: String,
    /** 当前请求花费时间 */
    val costTime: Long,
    /** 请求返回体 */
    val body: T
)