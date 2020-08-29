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
data class Profile(
    var accountStatus: Int?,
    var authStatus: Int?,
    var authority: Int?,
    var avatarImgId: Long?,
    var avatarImgIdStr: String?,
    var avatarImgId_str: String?,
    var avatarUrl: String?,
    var backgroundImgId: Long?,
    var backgroundImgIdStr: String?,
    var backgroundUrl: String?,
    var birthday: Long?,
    var city: Int?,
    var defaultAvatar: Boolean?,
    var description: String?,
    var detailDescription: String?,
    var djStatus: Int?,
    var eventCount: Int?,
    var expertTags: Any?,
    var experts: Experts?,
    var followed: Boolean?,
    var followeds: Int?,
    var follows: Int?,
    var gender: Int?,
    var mutual: Boolean?,
    var nickname: String?,
    var playlistBeSubscribedCount: Int?,
    var playlistCount: Int?,
    var province: Int?,
    var remarkName: Any?,
    var signature: String?,
    var userId: Int?,
    var userType: Int?,
    var vipType: Int?
)

@Keep
class Experts(
)