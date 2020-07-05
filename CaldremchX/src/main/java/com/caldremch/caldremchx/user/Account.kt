package com.caldremch.caldremchx.user
import androidx.annotation.Keep


/**
 * @author Caldremch
 * @date  2020/7/5
 * @email caldremch@163.com
 * @describe
 *
 **/
@Keep
data class Account(
    var anonimousUser: Boolean?,
    var ban: Int?,
    var baoyueVersion: Int?,
    var createTime: Int?,
    var donateVersion: Int?,
    var id: Int?,
    var salt: String?,
    var status: Int?,
    var tokenVersion: Int?,
    var type: Int?,
    var userName: String?,
    var vipType: Int?,
    var viptypeVersion: Long?,
    var whitelistAuthority: Int?
)