package xyz.thetbw.none.system.service

import xyz.thetbw.none.system.data.LoginUser
import xyz.thetbw.none.system.data.domain.SysUser

interface TokenService {

    /**
     * 根据当前用户创建一个token
     */
    fun createToken(user: SysUser): String

    /**
     * 刷新token
     */
    fun refreshToken(user: LoginUser)

    /**
     * 根据token和角色 检验并刷新token
     */
    fun verifyToken(token: String,role: String?): Boolean

    /**
     * 根据 token和 角色获取当前登录的用户
     */
    fun getLoginUser(token: String,role: String?): LoginUser?

}