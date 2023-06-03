package com.w83ll43.musicplayer.ui.home.more.tab_layout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.RecommendPlayList

class MoreFragmentOneViewModel: ViewModel() {
    private val songLiveData = MutableLiveData<String>()

    val playList = ArrayList<RecommendPlayList.Result>()

    val playListLiveData = Transformations.switchMap(songLiveData) {
        Repository.getRecommendPlayList()
    }

    fun getRecommendPlayList() {
        playList.clear()
        songLiveData.value = "1"
    }

}