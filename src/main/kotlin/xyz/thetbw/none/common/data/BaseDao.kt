package xyz.thetbw.none.common.data

import io.ebean.DB
import io.ebean.Finder
import io.ebean.PagedList
import io.ebean.Query
import mu.KotlinLogging

open class BaseDao<I,T>(protected val beanClass:Class<T>): Finder<I,T>(beanClass) {

    private val logger  = KotlinLogging.logger {  }

    /**
     * 初始化
     */
    init {
        //创建数据库
        logger.info { "开始创建数据库，当前Bean:${beanClass.canonicalName}" }
        DB.getDefault().beginTransaction().use {

        }
    }

    /**
     * 获取当前Bean的基本查询
     */
    protected fun getBaseQuery(): Query<T> = DB.find(beanClass)


    /**
     * 基本的查询列表
     */
    fun baseList(): PagedList<T> {
        val query  = getBaseQuery();
        query.firstRow = 0
        query.maxRows  = 20
        return query.findPagedList()
    }

}