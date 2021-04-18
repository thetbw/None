package xyz.thetbw.none.common.utils

import com.google.gson.Gson


/**
 * 将对象转换为json
 */
fun <T> T.toJson(): String{
    return Gson().toJson(this)
}