package com.caldremch.simplehttp.customhttp

import com.caldremch.custom.IConvert
import com.caldremch.exception.ApiHttpException
import com.caldremch.exception.NullDataSuccessException
import com.google.gson.GsonBuilder
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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
            val dataJson = jsonObject["message"]
            val respType = jsonObject["type"]

            if (respType != null) {

                val status = respType.asString



                if ("ok" != status) {

                    //错误处理
                    //抛出后, 在 Observer 中处理
                    var errorMsg: String? = ""
                    var errorCode: Int = -1
                    if (jsonObject["message"] != null) {
                        val errorMessage = jsonObject["message"] as JsonObject
                        errorCode = errorMessage["code"].asInt
                        errorMsg = errorMessage["errmsg"].asString
                    }
                    throw ApiHttpException(errorCode, errorMsg)
                }

                if ((dataJson == null || dataJson is JsonNull) && "ok" == status) {
                    throw NullDataSuccessException()
                }
            }

            if (dataJson == null || dataJson is JsonNull) throw NullDataSuccessException()

            val dataStr = dataJson.toString()

            return if (type == String::class.java) {
                dataStr as T
            } else {
                mGson.fromJson(dataStr, type)
            }
        }
    }
}