package com.w83ll43.musicplayer.logic.model.response

import com.w83ll43.musicplayer.logic.model.Song

data class SearchResponse(
    val code: Int,
    val result: Result
)

data class Result(
    val hasMore: Boolean,
    val songCount: Int,
    val songs: List<Song>
)

data class Album(
    val artist: Artist,
    val copyrightId: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val picId: Long,
    val publishTime: Long,
    val size: Int,
    val status: Int
)

data class Artist(
    val albumSize: Int,
    val alias: List<Any>,
    val fansGroup: Any,
    val id: Int,
    val img1v1: Int,
    val img1v1Url: String,
    val name: String,
    val picId: Int,
    val picUrl: Any,
    val trans: Any
)