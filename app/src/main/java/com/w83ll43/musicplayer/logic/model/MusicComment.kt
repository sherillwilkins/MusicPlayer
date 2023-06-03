package com.w83ll43.musicplayer.logic.model

data class MusicComment(
    val cnum: Int,
    val code: Int,
    val commentBanner: Any,
    val comments: List<Comment>,
    val hotComments: List<HotComment>,
    val isMusician: Boolean,
    val more: Boolean,
    val moreHot: Boolean,
    val topComments: List<Any>,
    val total: Int,
    val userId: Long
)

data class Comment(
    val beReplied: List<BeReplied>,
    val commentId: Long,
    val commentLocationType: Int,
    val content: String,
    val contentResource: Any,
    val decoration: Decoration,
    val expressionUrl: Any,
    val grade: Any,
    val ipLocation: IpLocationX,
    val liked: Boolean,
    val likedCount: Int,
    val needDisplayTime: Boolean,
    val owner: Boolean,
    val parentCommentId: Long,
    val pendantData: Any,
    val repliedMark: Any,
    val richContent: Any,
    val showFloorComment: Any,
    val status: Int,
    val time: Long,
    val timeStr: String,
    val user: User,
    val userBizLevels: Any
)

data class HotComment(
    val beReplied: List<BeReplied>,
    val commentId: Long,
    val commentLocationType: Int,
    val content: String,
    val contentResource: Any,
    val decoration: Decoration,
    val expressionUrl: Any,
    val grade: Any,
    val ipLocation: IpLocationX,
    val liked: Boolean,
    val likedCount: Int,
    val needDisplayTime: Boolean,
    val owner: Boolean,
    val parentCommentId: Int,
    val pendantData: PendantData,
    val repliedMark: Any,
    val richContent: Any,
    val showFloorComment: Any,
    val status: Int,
    val time: Long,
    val timeStr: String,
    val user: User,
    val userBizLevels: Any
)

data class BeReplied(
    val beRepliedCommentId: Long,
    val content: String,
    val expressionUrl: Any,
    val ipLocation: IpLocationX,
    val richContent: Any,
    val status: Int,
    val localUser: LocalUser
)

class Decoration

data class IpLocationX(
    val ip: Any,
    val location: String,
    val userId: Any
)

data class User(
    val anonym: Int,
    val authStatus: Int,
    val avatarDetail: AvatarDetail,
    val avatarUrl: String,
    val commonIdentity: Any,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val liveInfo: Any,
    val locationInfo: Any,
    val mutual: Boolean,
    val nickname: String,
    val remarkName: Any,
    val socialUserId: Any,
    val target: Any,
    val userId: Long,
    val userType: Int,
    val vipRights: VipRights,
    val vipType: Int
)

data class VipRights(
    val associator: Associator,
    val musicPackage: MusicPackage,
    val redVipAnnualCount: Int,
    val redVipLevel: Int,
    val redplus: Any
)

data class Associator(
    val iconUrl: String,
    val rights: Boolean,
    val vipCode: Int
)

data class MusicPackage(
    val iconUrl: String,
    val rights: Boolean,
    val vipCode: Int
)

data class PendantData(
    val id: Int,
    val imageUrl: String
)


data class AvatarDetail(
    val identityIconUrl: String,
    val identityLevel: Int,
    val userType: Int
)

