package com.w83ll43.musicplayer.logic.model

import com.w83ll43.musicplayer.logic.model.response.Album
import com.w83ll43.musicplayer.logic.model.response.Artist

data class NowPlayInfo(val name: String, val id: Int, val ar: List<Ar>, val al: Al, val artist: List<Artist>, val album: Album) {
    data class Al(
        val id: Int,
        val name: String,
        val pic: Long,
        val picUrl: String,
        val pic_str: String,
        val tns: List<Any>
    )

    data class Ar(
        val id: Int,
        val name: String
    )
}