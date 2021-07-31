package xyz.thetbw.none.common.data

import io.ebean.Model
import io.ebean.annotation.*
import java.sql.Timestamp
import java.util.*
import javax.persistence.Id
import javax.persistence.MappedSuperclass


/**
 * 基本Entity类
 */
@MappedSuperclass
open class BaseEntity: Model() {

    @Id
    lateinit  var id:String

    /**
     * 创建时间
     */
    @WhenCreated
    lateinit var whenCreated:Timestamp

    /**
     * 修改时间
     */
    @WhenModified
    lateinit var whenModified:Timestamp


}