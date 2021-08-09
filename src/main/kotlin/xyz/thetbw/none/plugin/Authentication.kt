package xyz.thetbw.none.plugin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import org.kodein.di.instance
import xyz.thetbw.none.common.SysUserRole
import xyz.thetbw.none.common.TOKEN_CLAIM_USER_TOKEN
import xyz.thetbw.none.common.utils.Config
import xyz.thetbw.none.di
import xyz.thetbw.none.excetion.AuthException
import xyz.thetbw.none.system.data.LoginUser
import xyz.thetbw.none.system.service.TokenService

/**
 * 角色认证相关配置
 */
fun Application.authentication(){
    /** token相关 */
    val authSecret = Config.getAsString("ktor.jwt.secret")
    val tokenService by di.instance<TokenService>()

    install(RoleBasedAuthorization)
    install(Authentication){
        jwt{
            /** 校验签名 */
            verifier(
                JWT
                .require(Algorithm.HMAC256(authSecret))
                .build())
            /** 校验数据 */
            validate { credential ->
                val userToken = credential.payload.getClaim(TOKEN_CLAIM_USER_TOKEN).asString()
                tokenService.getLoginUser(userToken, null)
            }
        }
    }
}

class AuthorizedRouteSelector(private val description: Array<out SysUserRole?>) :
    RouteSelector(RouteSelectorEvaluation.qualityConstant) {
    override fun evaluate(context: RoutingResolveContext, segmentIndex: Int) = RouteSelectorEvaluation.Constant
    override fun toString(): String = "(authorize ${description.joinToString { it!!.roleName }})"
}

/**
 * 基于角色的验证
 * https://github.com/ximedes/ktor-authorization
 */
class RoleBasedAuthorization(config: Configuration){

    class Configuration

    fun interceptPipeline(
        pipeline: ApplicationCallPipeline,
        roles: Array<out SysUserRole?>
    ) {
        pipeline.insertPhaseAfter(ApplicationCallPipeline.Features, Authentication.ChallengePhase)
        pipeline.insertPhaseAfter(Authentication.ChallengePhase, AuthorizationPhase)

        pipeline.intercept(AuthorizationPhase) {
            //检查当前用户角色是否正确
            val user = this.call.principal<LoginUser>() ?: throw AuthException("请先登录")

            if (roles.isNotEmpty() && !roles.contains(user.getRole())){
                throw AuthException("没有权限")
            }
            proceed()
        }
    }

    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, RoleBasedAuthorization> {
        override val key = AttributeKey<RoleBasedAuthorization>("RoleBasedAuthorization")

        val AuthorizationPhase = PipelinePhase("Authorization")

        override fun install(
            pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit
        ): RoleBasedAuthorization {
            val configuration = Configuration().apply(configure)
            return RoleBasedAuthorization(configuration)
        }
    }

}

/**
 * 检查一个用户是否是指定的角色，当前用户必须登录
 * @param roles 允许访问的角色，为空则不检查角色，只检测必须登录
 */
fun Route.withRole(vararg roles: SysUserRole? = arrayOf(), build: Route.()->Unit): Route{
    val authorizedRoute = createChild(AuthorizedRouteSelector(roles))
    application.feature(RoleBasedAuthorization).interceptPipeline(authorizedRoute,roles)
    authorizedRoute.build()
    return authorizedRoute

}