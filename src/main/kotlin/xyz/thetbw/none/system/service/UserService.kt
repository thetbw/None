package xyz.thetbw.none.system.service

interface UserService {

    /**
     * 查询用户信息
     * @param name 用户名称
     */
    fun queryUser(name: String):Any

    /**
     * 创建用户
     */
    fun addUser()
}