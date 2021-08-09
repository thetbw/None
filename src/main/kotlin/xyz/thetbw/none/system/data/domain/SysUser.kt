package xyz.thetbw.none.system.data.domain

import xyz.thetbw.none.common.data.SoftDeleteEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "sys_user")
class SysUser : SoftDeleteEntity() {

    /**
     * 登录名称
     */
    @Column(length = 32)
    var name: String = ""
        get() {
            return "";
        }

    /**
     * 昵称
     */
    @Column(length = 32)
    lateinit var nickName: String

    /**
     * 密码
     */
    @Column(length = 64)
    @Transient
    lateinit var password: String

    /**
     * 邮箱
     */
    @Column(length = 128)
    lateinit var email: String

    /**
     * 性别
     */
    var gender: Int = 0

    /**
     * 角色信息s
     */
    lateinit var role: String

}