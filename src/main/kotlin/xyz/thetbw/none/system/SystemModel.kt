package xyz.thetbw.none.system

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import xyz.thetbw.none.system.data.domain.dao.SysUserDao
import xyz.thetbw.none.system.service.UserService
import xyz.thetbw.none.system.service.impl.UserServiceImpl

val systemModel = DI.Module("system"){
    //注册service
    bind<UserService> { singleton { UserServiceImpl() }}

    //注册finder
    bind<SysUserDao> { singleton { SysUserDao() }}
}