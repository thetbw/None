package xyz.thetbw.none.system.data.entities

import org.jetbrains.exposed.sql.Table

/**
 * 用户表
 */
object User : Table() {
    val id = varchar("id", 20).nullable()
    val name = varchar("name", 32).nullable()
    val nickName = varchar("nickname", 32).nullable()
    val password = varchar("password", 32).nullable()
    val roleId = varchar("role_id", 20).nullable().index("user_role_id")

    override val primaryKey = PrimaryKey(id, name = "user_id")
}