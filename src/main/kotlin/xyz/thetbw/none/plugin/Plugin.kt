package xyz.thetbw.none.plugin

import io.ktor.application.*
import xyz.thetbw.none.ApplicationHolder
import xyz.thetbw.none.initModels


fun Application.init(){
    /** 初始化ApplicationHolder **/
    ApplicationHolder.init(this)
    initModels()

    /**
     * 加载其他插件
     */
    authentication()
    dataSource()
    freemarker()
    serialization()
    responseWrap()
    routing()
}