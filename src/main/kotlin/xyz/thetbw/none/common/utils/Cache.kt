package xyz.thetbw.none.common.utils

import mu.KotlinLogging
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import xyz.thetbw.none.plugin.fromJsonByType
import xyz.thetbw.none.plugin.toJson
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

val logger = KotlinLogging.logger { }


fun <T> catchable(func: () -> T): T {
    println(func.javaClass.name)
    return func()
}


interface CacheService {

    /**
     * 设置缓存
     */
    fun setValue(key: String, value: Any, expiredTime: Long?, timeUnit: TimeUnit = TimeUnit.SECONDS)

    /**
     * 当元素不存在时设置值
     */
    fun setValueNX(key: String, value: String, expiredTime: Long?, timeUnit: TimeUnit = TimeUnit.SECONDS): Boolean

    /**
     * 重新设置缓存值
     */
    fun expire(key: String, expiredTime: Long, timeUnit: TimeUnit = TimeUnit.SECONDS)

    /**
     * 获取缓存对象
     */
    fun <T: Any> getValue(key: String,type: KClass<T>): T?

    /**
     * 删除缓存对象
     */
    fun deleteValue(key: String): Boolean

}

class RedisCacheService : CacheService {
    private lateinit var jedisPool: JedisPool

    init {
        val url: String? = Config.getAsString("ktor.redis.url")
        if (url == null) {
            logger.warn { "没有配置redis连接地址,redis 缓存将不可用" }
        } else {
            jedisPool = JedisPool(JedisPoolConfig(), url)
        }
    }

    /**
     * redis缓存是否可用
     */
    fun available(): Boolean {
        return this::jedisPool.isInitialized
    }

    override fun setValue(key: String, value: Any, expiredTime: Long?, timeUnit: TimeUnit) {
        if (!available()) return
        jedisPool.resource.use {
            if (expiredTime == null){
                it.set(key,value.toJson())
            }else{
                it.setex(key,timeUnit.toSeconds(expiredTime),value.toJson())
            }
        }

    }

    override fun setValueNX(key: String, value: String, expiredTime: Long?, timeUnit: TimeUnit):Boolean {
        if (!available()) return false
        jedisPool.resource.use {
            return if (expiredTime == null){
                it.setnx(key,value.toJson()).toInt() == 1
            }else{
                val result = it.setnx(key,value.toJson())
                if (result.toInt() == 0){
                    it.expire(key,timeUnit.toSeconds(expiredTime))
                    true
                }else{
                    false
                }

            }
        }
    }

    override fun expire(key: String, expiredTime: Long, timeUnit: TimeUnit) {
        if (!available()) return
        jedisPool.resource.use {
            it.expire(key,timeUnit.toSeconds(expiredTime))
        }
    }

     override fun <T: Any> getValue(key: String,type: KClass<T>): T? {
        if (!available()) return null
        jedisPool.resource.use {
            return it.get(key)?.fromJsonByType(type)
        }
    }

    override fun deleteValue(key: String): Boolean {
        if (!available()) return false
        jedisPool.resource.use {
            return it.del(key) > 0
        }
    }
}

class JavaCacheService : CacheService {

    override fun setValue(key: String, value: Any, expiredTime: Long?, timeUnit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun setValueNX(key: String, value: String, expiredTime: Long?, timeUnit: TimeUnit): Boolean {
        TODO("Not yet implemented")
    }

    override fun expire(key: String, expiredTime: Long, timeUnit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> getValue(key: String,type: KClass<T>): T? {
        TODO("Not yet implemented")
    }


    override fun deleteValue(key: String): Boolean {
        TODO("Not yet implemented")
    }
}

class WeakCacheService : CacheService {

    override fun setValue(key: String, value: Any, expiredTime: Long?, timeUnit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun setValueNX(key: String, value: String, expiredTime: Long?, timeUnit: TimeUnit): Boolean {
        TODO("Not yet implemented")
    }

    override fun expire(key: String, expiredTime: Long, timeUnit: TimeUnit) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> getValue(key: String,type: KClass<T>): T? {
        TODO("Not yet implemented")
    }


    override fun deleteValue(key: String): Boolean {
        TODO("Not yet implemented")
    }
}

