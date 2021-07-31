package xyz.thetbw.none.system.service.impl

import io.ebean.PagedList
import org.kodein.di.instance
import xyz.thetbw.none.di
import xyz.thetbw.none.system.data.domain.SysUser
import xyz.thetbw.none.system.data.domain.dao.SysUserDao
import xyz.thetbw.none.system.service.UserService

class UserServiceImpl : UserService {

    //用户dao
    val sysUserDao: SysUserDao by di.instance()

    override fun listUser(): PagedList<SysUser> {
        return sysUserDao.baseList()
    }

    override fun getUser(id: String): SysUser {
        TODO("Not yet implemented")
    }

    override fun addUser(json: String): SysUser {
        TODO("Not yet implemented")
    }

    override fun deleteUser(ids: String): Int {
        TODO("Not yet implemented")
    }

    override fun updateUser(json: String): SysUser {
        TODO("Not yet implemented")
    }


}