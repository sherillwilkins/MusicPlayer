package com.w83ll43.musicplayer.ui.playList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.Song

class PlayListViewModel : ViewModel() {
    private val songsId = MutableLiveData<String>()
    val playSongList = ArrayList<Song>()

//    private val songId = MutableLiveData<String>()
//    var playMusicUrl = ArrayList<MusicUrl>()

//    val playSongLiveData = Transformations.switchMap(songId) {
//        Repository.getMusicUrl(it)
//    }

//    fun getSongMusicUrl(data: String) {
//        songId.value = data
//    }

    // 拿到从仓库层返的liveData
    val playSongListLiveData = Transformations.switchMap(songsId) {
        Repository.getPlayListDetail(it)
    }

    fun getPlayListTrack(data: String) {
        songsId.value = data
    }
}