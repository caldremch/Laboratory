package com.caldremch.simplehttp.customhttp

import com.caldremch.custom.IConvert
import com.caldremch.exception.ApiHttpException
import com.caldremch.exception.NullDataSuccessException
import com.google.gson.GsonBuilder
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.lang.reflect.Type

/**
 *
 * @author Caldremch
 *
 * @date 2020-01-10 10:32
 *
 * @email caldremch@163.com
 *
 * @describe
 *
 **/
class SampleConvert2 : IConvert {

    private val mGson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
    private val mParser = JsonParser()

    override fun <T> convert(responseBody: ResponseBody, type: Type): T {

        responseBody.use {
            val jsonRespStr: String = responseBody.string()
            val jsonObject = mParser.parse(jsonRespStr).asJsonObject
            val code = jsonObject["code"].asInt
            val message = jsonObject["message"]
            val msg = jsonObject["msg"]

            if (code == 200) {
                //正常
                val map: Map<String, Any?> = mGson.fromJson(
                    jsonRespStr, object : TypeToken<HashMap<String, Any?>>() {}.type
                )
                return map as T
            } else {

                var finalMsg: Any? = message
                if (message == null || message is JsonNull) {
                    finalMsg = msg
                }
                //错误
                if (finalMsg == null || finalMsg is JsonNull) {
                    throw ApiHttpException(code, "发生了错误")
                } else {
                    throw ApiHttpException(code, finalMsg.toString())
                }
            }
        }
    }
}