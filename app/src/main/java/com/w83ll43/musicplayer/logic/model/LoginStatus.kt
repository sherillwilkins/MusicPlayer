package com.w83ll43.musicplayer.logic.model

data class LoginStatus(
    val data: Data
) {
    data class Data(val code: Int, val account: Account, val profile: Profile)
}