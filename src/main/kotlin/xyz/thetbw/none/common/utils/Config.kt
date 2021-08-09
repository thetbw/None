package xyz.thetbw.none.common.utils

import mu.KotlinLogging
import xyz.thetbw.none.ApplicationHolder

/**
 * 配置信息工具类
 */
object Config {

    private val logger = KotlinLogging.logger{}

    /**
     * 获取配置值
     * @param configName 配置名称
     * @param defaultValue 默认值
     */
    fun getAsString(configName: String, defaultValue: String? = null, notNull: Boolean = false): String? {
        //TODO        contract
        logger.debug { "" }
        val config = ApplicationHolder.getApplication().environment.config
        return config.propertyOrNull(configName)?.getString() ?: defaultValue
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