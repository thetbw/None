package xyz.thetbw.none.system.dao

import xyz.thetbw.none.common.data.BaseDao
import xyz.thetbw.none.system.data.domain.SysUser

/**
 * 系统用户finder
 */
class SysUserDao: BaseDao<String,SysUser>(SysUser::class.java) {


    fun doo(){
        val a = beanClass;
        getBaseQuery()
    }
}