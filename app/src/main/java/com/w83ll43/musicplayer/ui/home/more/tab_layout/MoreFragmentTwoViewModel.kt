package com.w83ll43.musicplayer.ui.home.more.tab_layout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.HotTopList

class MoreFragmentTwoViewModel : ViewModel() {
    private val type = MutableLiveData<String>()
    private val hotTopData = ArrayList<HotTopList.HopTopDetail>()
    val loading = MutableLiveData<Boolean>()

    val hotTopListLiveData : LiveData<Result<HotTopList>> = Transformations.switchMap(type) {
        loading.value = false
        Repository.getTopList()
    }
    val hotTopList: ArrayList<HotTopList.HopTopDetail>
        get() = hotTopData

    fun getTopList() {
        loading.value = true
        type.value = "1"
    }

    fun setHotTopList(data: List<HotTopList.HopTopDetail>) {
        hotTopList.clear()
        val list = data.filter {
            it.tracks.isNotEmpty()
        }
        hotTopData.addAll(data)
    }
}