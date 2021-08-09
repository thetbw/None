package xyz.thetbw.none.system.data

import cn.hutool.core.date.DateUtil
import com.google.gson.annotations.Expose
import io.ktor.auth.*
import xyz.thetbw.none.common.SysUserRole
import xyz.thetbw.none.common.utils.Config
import xyz.thetbw.none.excetion.InnerException
import xyz.thetbw.none.system.data.domain.SysUser


data class LoginUser(
    /** 当前用户 */
    val user: SysUser,
    /** 当前用户 token */
    val token: String
) : Principal {

    @Transient
    private  val _expireTime = Config.getAsString("ktor.jwt.expireTime")!!.toInt()
    /** 过期时间*/
    var expiredTime: Long = DateUtil.currentSeconds() + _expireTime


    /** 获取当前用户的角色 */
    fun getRole(): SysUserRole {
        SysUserRole.values().forEach {
            if (user.role == it.roleName){
                return it
            }
        }
        return SysUserRole.GUEST
    }



    /**
     * 更新过期时间
     */
    fun updateExpiredTime(){
        expiredTime = DateUtil.currentSeconds() + _expireTime
    }
}
