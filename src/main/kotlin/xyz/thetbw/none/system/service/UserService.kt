package xyz.thetbw.none.system.service

import io.ebean.PagedList
import xyz.thetbw.none.system.data.domain.SysUser

interface UserService {

    /**
     * 查询用户列表
     * @return 返回分页的用户列表
     */
    fun listUser(): PagedList<SysUser>

    /**
     * 获取用户详情
     *
     * @return 返回用户信息
     */
    fun getUser(id: String): SysUser

    /**
     *  创建用户
     *
     *  @return 返回创建的用户信息
     */
    fun addUser(json: String): SysUser

    /**
     * 删除用户
     *@return 返回删除的用户数量
     */
    fun deleteUser(ids: String): Int

    /**
     * 更新用户
     *@return 返回更新后的用户信息
     */
    fun updateUser(json: String): SysUser

}