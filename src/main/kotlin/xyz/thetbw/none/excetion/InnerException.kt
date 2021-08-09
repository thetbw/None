package xyz.thetbw.none.excetion

/**
 * 代码未知异常
 */
open class InnerException: RuntimeException {
    constructor(): super()
    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable): super(msg,cause)
    constructor(cause: Throwable): super(cause)
}