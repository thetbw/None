package xyz.thetbw.none.plugin

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.config.dbplatform.h2.H2Platform
import io.ebean.config.dbplatform.mysql.MySqlPlatform
import io.ktor.application.*
import xyz.thetbw.none.common.utils.Config

/**
 * 数据库配置
 */
fun Application.dataSource(){
    /** 初始化数据库链接池 */
    let {
        //获取配置文件
        val config = HikariConfig()
        config.driverClassName = Config.getAsString("ktor.datasource.driver")
        config.jdbcUrl = Config.getAsString("ktor.datasource.url")
        config.username = Config.getAsString("ktor.datasource.username")
        config.password = Config.getAsString("ktor.datasource.password")

        val properties  = Config.getAsList("ktor.datasource.properties")
        properties.forEach {
            val param = it.split(":")
            config.addDataSourceProperty(param[0],param[1])
        }

        //连接数据库
        val datasource = HikariDataSource(config)
        val databaseConfig  = DatabaseConfig()
        databaseConfig.isDefaultServer  = true
        databaseConfig.dataSource = datasource
//        databaseConfig.isRunMigration = true
        //自动创建数据库，仅在测试模式可用
//        databaseConfig.isDdlRun = true
//        databaseConfig.isDdlGenerate = true

        databaseConfig.isRegister = true
        if (Config.getAsString("ktor.datasource.platform") == "H2"){
            databaseConfig.databasePlatform = H2Platform()
        }else {
            databaseConfig.databasePlatform = MySqlPlatform()
        }
        DatabaseFactory.create(databaseConfig)
    }
}