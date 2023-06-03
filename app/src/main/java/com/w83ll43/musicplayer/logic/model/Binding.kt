package com.w83ll43.musicplayer.logic.model

data class Binding(
    val expiresIn: Int,
    val refreshTime: Int,
    val bindingTime: Long,
    val tokenJsonStr: String,
    val expired: Boolean,
    val url: String,
    val userId: Int,
    val id: Long,
    val type: Int
)