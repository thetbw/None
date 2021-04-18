package xyz.thetbw.none.system.service.impl

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.thetbw.none.system.data.entities.User
import xyz.thetbw.none.system.data.entities.User.nickName
import xyz.thetbw.none.system.data.entities.User.password
import xyz.thetbw.none.system.data.entities.User.roleId
import xyz.thetbw.none.system.service.UserService

class UserServiceImpl : UserService {


    override fun queryUser(name: String): Any {
        val result = ArrayList<Map<String, String>>()
        transaction {
            User.selectAll().map {
                val row = mapOf<String, String>(
                    "id" to (it[User.id] ?: ""),
                    "name" to (it[User.name] ?: ""),
                    "nickname" to (it[nickName] ?: ""),
                    "password" to (it[password] ?: ""),
                    "ruleId" to (it[roleId] ?: "")
                )
                result.add(row)
            }
        }
        return result
    }

    override fun addUser() {
        transaction {
            User.insert {
                it[id] = "asasa" + System.currentTimeMillis()
                it[name] = "张三"
                it[nickName] = "黑与"
                it[password] = "asas"
                it[roleId] = "asa"
            }
        }
    }


}