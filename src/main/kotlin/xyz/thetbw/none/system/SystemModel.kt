package xyz.thetbw.none.system

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import xyz.thetbw.none.system.dao.SysUserDao
import xyz.thetbw.none.system.service.TokenService
import xyz.thetbw.none.system.service.UserService
import xyz.thetbw.none.system.service.impl.TokenServiceImpl
import xyz.thetbw.none.system.service.impl.UserServiceImpl

val systemModel = DI.Module("system"){

    bind<UserService>{ singleton { UserServiceImpl()}}
    bind<TokenService>{ singleton { TokenServiceImpl() }}

    bind{  singleton { SysUserDao() }}
}