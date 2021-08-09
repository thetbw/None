package xyz.thetbw.none.common


/**
 * 用户角色
 */
enum class SysUserRole(val roleName: String){

    /** 管理员 */
    ADMIN("admin"),

    /** 会员 */
    MEMBER("member"),

    /** 游客 */
    GUEST("guest")
}

/**
 * 缓存key前缀
 */
const val CACHE_KEYS_PREFIX_FIELD= "FIELD_CACHE_KEY_"  //字段委托缓存key
const val CACHE_KEYS_PREFIX_METHOD= "METHOD_CACHE_KEY_" //方法缓存 key
const val CACHE_KEYS_PREFIX_TOKEN = "TOKEN_CACHE_KEY_"  // token key前缀


const val TOKEN_CLAIM_USER_TOKEN = "user_token"