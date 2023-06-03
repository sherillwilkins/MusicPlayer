package com.w83ll43.musicplayer.logic.model

data class MusicUrl(
    val code: Int,
    val `data`: List<Data>
)

data class Data(
    val br: Int,
    val canExtend: Boolean,
    val code: Int,
    val effectTypes: Any,
    val encodeType: String,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
    val freeTrialInfo: Any,
    val freeTrialPrivilege: FreeTrialPrivilege,
    val gain: Double,
    val id: Int,
    val level: String,
    val md5: String,
    val payed: Int,
    val peak: Double,
    val podcastCtrp: Any,
    val rightSource: Int,
    val size: Int,
    val time: Int,
    val type: String,
    val uf: Any,
    val url: String,
    val urlSource: Int
)

data class FreeTimeTrialPrivilege(
    val remainTime: Int,
    val resConsumable: Boolean,
    val type: Int,
    val userConsumable: Boolean
)

data class FreeTrialPrivilege(
    val cannotListenReason: Any,
    val listenType: Any,
    val resConsumable: Boolean,
    val userConsumable: Boolean
)