package xyz.thetbw.none.excetion

/**
 * 用户操作异常
 */
open class UserException: RuntimeException {
    constructor(): super()
    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable): super(msg,cause)
    constructor(cause: Throwable): super(cause)
}