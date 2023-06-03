package com.w83ll43.musicplayer.ui.home.find

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.Playlist
import com.w83ll43.musicplayer.logic.model.Song

class FindViewModel : ViewModel() {
    // 获取播放歌单
    private val userUID = MutableLiveData<String>()

    val playList = ArrayList<Playlist>()

    val playListLiveData = Transformations.switchMap(userUID) {
        Repository.getPlayList()
    }

    fun getPlayList(data: String) {
        playList.clear()
        userUID.value = data
    }

    // 搜索功能
    private val keyword = MutableLiveData<String>()


    val searchResult = ArrayList<Song>()

    val keyWordLiveData = Transformations.switchMap(keyword) {
        Repository.search(it)
    }

    fun searchKeyword(data: String) {
        keyword.value = data
    }

    val getSearchKey: String?
        get() = keyword.value
}