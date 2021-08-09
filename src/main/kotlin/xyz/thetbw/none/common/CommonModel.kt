package xyz.thetbw.none.common

import org.kodein.di.*
import xyz.thetbw.none.common.utils.CacheService
import xyz.thetbw.none.common.utils.JavaCacheService
import xyz.thetbw.none.common.utils.RedisCacheService
import xyz.thetbw.none.common.utils.WeakCacheService

typealias  CacheServicePair = Pair<String, CacheService>

val commonModel = DI.Module("common"){

    bind { singleton { RedisCacheService() } }
    bind { singleton {  JavaCacheService() } }
    bind { singleton { WeakCacheService() } }
}

