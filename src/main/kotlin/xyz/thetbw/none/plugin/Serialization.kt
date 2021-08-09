package xyz.thetbw.none.plugin

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import kotlin.reflect.KClass

/**
 * 序列化相关配置
 */
fun Application.serialization(){
    /** 使用 kotlin serialization */
    install(ContentNegotiation){
        gson{
            serializeNulls()
        }
    }
}

fun Any.toJson(): String{
    return Gson().toJson(this)
}

@Suppress("unchecked")
fun <T : Any> String.fromJsonByType(type: KClass<T>): T {
    return Gson().fromJson(this,type.java)
}

inline fun <reified T> String.fromJson(): T{
    return Gson().fromJson(this,T::class.java)
}