package xyz.thetbw.none.system.service.impl

import cn.hutool.core.date.DateUtil
import cn.hutool.core.lang.UUID
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.kodein.di.instance
import xyz.thetbw.none.common.TOKEN_CLAIM_USER_TOKEN
import xyz.thetbw.none.common.utils.Config
import xyz.thetbw.none.common.utils.RedisCacheService
import xyz.thetbw.none.di
import xyz.thetbw.none.excetion.InnerException
import xyz.thetbw.none.system.data.LoginUser
import xyz.thetbw.none.system.data.domain.SysUser
import xyz.thetbw.none.system.service.TokenService

class TokenServiceImpl: TokenService {

    private val authSecret = Config.getAsString("ktor.jwt.secret")
    private val _expireTime = Config.getAsString("ktor.jwt.expireTime")!!.toLong()
    private val cacheService: RedisCacheService by di.instance()

    private val MILLIS_MINUTE =  60 * 20

    override fun createToken(user: SysUser): String {
        val userToken = UUID.randomUUID().toString()
        val loginUser = LoginUser(user,userToken)
        refreshToken(loginUser)
        return JWT.create().withClaim(TOKEN_CLAIM_USER_TOKEN,userToken).sign(Algorithm.HMAC256(authSecret))
    }

    override fun refreshToken(user: LoginUser) {
        val token =  user.token
        user.updateExpiredTime()
        cacheService.setValue(token,user,_expireTime)
    }

    override fun verifyToken(token: String, role: String?): Boolean {
        val user = cacheService.getValue(token,LoginUser::class)
        return user != null && (role == null || user.getRole().roleName == role)
    }

    override fun getLoginUser(token: String, role: String?): LoginUser? {
        val user = cacheService.getValue(token,LoginUser::class)
        return if (user != null && (role == null || user.getRole().roleName == role)){
            user.also {
                if (it.expiredTime - DateUtil.currentSeconds() < MILLIS_MINUTE ){
                    refreshToken(user)
                }
            }
        }else null
    }

}