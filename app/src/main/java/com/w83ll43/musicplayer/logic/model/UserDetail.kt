package com.w83ll43.musicplayer.logic.model

data class UserDetail(
    val level: Int,
    val listenSongs: Int,
    val userPoint: UserPoint,
    val mobileSign: Boolean,
    val pcSign: Boolean,
    val profile: Profile,
    val peopleCanSeeMyPlayRecord: Boolean,
    val bindings: List<Binding>,
    val adValid: Boolean,
    val code: Int,
    val newUser: Boolean,
    val recallUser: Boolean,
    val createTime: Long,
    val createDays: Int,
    val profileVillageInfo: ProfileVillageInfo
) {

    data class ProfileVillageInfo(val title: String, val imageUrl: String, val targetUrl: String)
}