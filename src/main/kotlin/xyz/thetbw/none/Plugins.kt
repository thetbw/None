package xyz.thetbw.none

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import freemarker.cache.ClassTemplateLoader
import io.ebean.DB
import io.ebean.DatabaseFactory
import io.ebean.EbeanServer
import io.ebean.EbeanServerFactory
import io.ebean.config.DatabaseConfig
import io.ebean.config.ServerConfig
import io.ebean.config.dbplatform.h2.H2Platform
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import mu.KotlinLogging
import xyz.thetbw.none.common.ApplicationHolder

import xyz.thetbw.none.common.utils.ConfigUtil
import xyz.thetbw.none.system.data.domain.SysUser
import java.util.*


private val logger = KotlinLogging.logger{}

/** 插件 & 依赖 集中配置 */
fun Application.plugin(){
    logger.info {"开始加载插件"}

    // 初始化ApplicationHolder
    ApplicationHolder.init(this)

    // freemarker配置
    install(FreeMarker){
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    //使用Gson
    install(ContentNegotiation){
        gson {  }
    }

    //初始化数据库链接池
    let {
        logger.info { "开始连接数据库" }
        //获取配置文件
        val config = HikariConfig()
        config.driverClassName = ConfigUtil.getAsString("ktor.datasource.driver")
        config.jdbcUrl = ConfigUtil.getAsString("ktor.datasource.url")
        config.username = ConfigUtil.getAsString("ktor.datasource.username")
        config.password = ConfigUtil.getAsString("ktor.datasource.password")



        val properties  = ConfigUtil.getAsList("ktor.datasource.properties");
        properties.forEach {
            val param = it.split(":");
            config.addDataSourceProperty(param[0],param[1])
        }

        //连接数据库
        val datasource = HikariDataSource(config)

        var databaseConfig  = DatabaseConfig()
        databaseConfig.loadFromProperties()
        databaseConfig.isDefaultServer  = true
        databaseConfig.dataSource = datasource
        //自动创建数据库
//        databaseConfig.isDdlCreateOnly = true
        databaseConfig.isDdlRun = true
        databaseConfig.isDdlGenerate = true

        databaseConfig.isRegister = true
        databaseConfig.databasePlatform = H2Platform()

//        val p = Properties()
//        p.setProperty("ebean.db.ddl.generate","true")
//        p.setProperty("ebean.db.ddl.run","true")
//
//        databaseConfig.loadFromProperties(p)

//        databaseConfig.ddl
        try {
            logger.info { "开始配置ebean" }
            DatabaseFactory.create(databaseConfig)
//            CurrentModel()
            logger.info { "ebean配置结束" }
        }catch (e: Exception){
            e.printStackTrace()
        }


//        Database.connect(datasource)
//
//        //初始化数据库
//        transaction {
//            SchemaUtils.create(User)
//        }


    }

    //记录请求日志
    install(CallLogging)


}
