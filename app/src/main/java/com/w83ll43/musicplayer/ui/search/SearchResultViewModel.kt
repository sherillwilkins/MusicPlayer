package com.w83ll43.musicplayer.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.Song

class SearchResultViewModel: ViewModel() {

    val searchResult = ArrayList<Song>()

    private val keyword = MutableLiveData<String>()

    val keyWordLiveData = Transformations.switchMap(keyword) {
        Repository.search(it)
    }

    fun searchKeyword(data: String) {
        keyword.value = data
    }

}