package com.w83ll43.musicplayer.logic.model

data class Profile(
    val privacyItemUnlimit: PrivacyItemUnlimit,
    val avatarDetail: Any,
    val avatarImgIdStr: String,
    val backgroundImgIdStr: String,
    val authStatus: Int,
    val detailDescription: String,
    val experts: Experts,
    val expertTags: Any,
    val description: String,
    val vipType: Int,
    val followed: Boolean,
    val userId: Int? = 572352988,
    val mutual: Boolean,
    val remarkName: Any,
    val birthday: Long,
    val gender: Int,
    val createTime: Long,
    val nickname: String,
    val accountStatus: Int,
    val province: Int,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundUrl: String,
    val userType: Int,
    val djStatus: Int,
    val city: Int,
    val defaultAvatar: Boolean,
    val signature: String,
    val authority: Int,
    val followeds: Int,
    val follows: Int,
    val blacklist: Boolean,
    val eventCount: Int,
    val allSubscribedCount: Int,
    val playlistBeSubscribedCount: Int,
    val avatarImgId_str: String,
    val followTime: Long,
    val followMe: Boolean,
    val artisIdentity: ArtisIdentity,
    val cCount: Int,
    val inBlacklist: Boolean,
    val sDJPCount: Int,
    val playlistCount: Int,
    val sCount: Int,
    val newFollows: Int
) {
    data class PrivacyItemUnlimit(
        val area: Boolean,
        val college: Boolean,
        val gender: Boolean,
        val age: Boolean,
        val villageAge: Boolean
    )

    class Experts

    class ArtisIdentity
}