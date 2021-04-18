package xyz.thetbw.none

import org.kodein.di.DI
import xyz.thetbw.none.system.systemModel

/**
 * 依赖管理
 */
val di = DI{
    import(systemModel)
}