package xyz.thetbw.none

import io.ktor.server.netty.*
import org.kodein.di.DI
import xyz.thetbw.none.common.commonModel
import xyz.thetbw.none.system.systemModel


lateinit var di: DI

fun initModels(){
    di = DI {
        import(systemModel)
        import(commonModel)
    }
}

/**
 * 程序入口
 */
fun main(args: Array<String>) {
    EngineMain.main(args)
}


