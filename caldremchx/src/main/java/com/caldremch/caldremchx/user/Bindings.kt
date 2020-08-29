package com.caldremch.caldremchx.user
import androidx.annotation.Keep


/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
class Bindings : ArrayList<BindingsItem>()

@Keep
data class BindingsItem(
    var bindingTime: Long?,
    var expired: Boolean?,
    var expiresIn: Int?,
    var id: Long?,
    var refreshTime: Int?,
    var tokenJsonStr: String?,
    var type: Int?,
    var url: String?,
    var userId: Int?
)