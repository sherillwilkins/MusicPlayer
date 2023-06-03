package com.w83ll43.musicplayer.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.Lyric


class PlayerViewModel : ViewModel() {
    private val songsId = MutableLiveData<Int>()
    private var objectData: MutableLiveData<Lyric> = MutableLiveData()
    val lyric: LiveData<Lyric>
        get() = objectData

    //拿到从仓库层返的liveData
    val lyricLiveData: LiveData<Result<Lyric>> = Transformations.switchMap(songsId) {
        Repository.getLyric(it)
    }

    //获取歌词
    fun getLyric(id: Int) {
        songsId.value = id
    }

    //设置歌词
    fun setLyric(data: Lyric) {
        objectData.value = data
    }
}