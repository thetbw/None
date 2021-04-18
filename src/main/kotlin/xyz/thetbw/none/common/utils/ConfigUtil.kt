package xyz.thetbw.none.common.utils

import mu.KotlinLogging
import xyz.thetbw.none.common.ApplicationHolder

/**
 * 配置信息工具类
 */
object ConfigUtil {

    private val logger = KotlinLogging.logger{}

    /**
     * 获取配置值
     * @param configName 配置名称
     * @param defaultValue 默认值
     */
    fun getAsString(configName: String, defaultValue: String? = null,notNull: Boolean = false): String? {
        //TODO        contract
        logger.debug { "" }
        val config = ApplicationHolder.getApplication().environment.config
        val value =  config.propertyOrNull(configName)?.getString()?: defaultValue
        logger.debug {
            "获取配置 $configName -> ${value?:"null"}"
        }
        return value
    }

    /**
     * 获取配置列表
     *@param configName 配置名称
     */
    fun getAsList(configName: String): List<String> {
        val config = ApplicationHolder.getApplication().environment.config
        return config.propertyOrNull(configName)?.getList() ?: ArrayList<String>(1)
    }
}