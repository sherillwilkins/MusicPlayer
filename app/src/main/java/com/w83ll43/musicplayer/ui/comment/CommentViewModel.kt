package com.w83ll43.musicplayer.ui.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository
import com.w83ll43.musicplayer.logic.model.Comment

class CommentViewModel : ViewModel() {
    private val songId = MutableLiveData<String>()
    var commentList = ArrayList<Comment>()

    val commentInfoLiveData = Transformations.switchMap(songId) {
        Repository.getMusicComment(it)
    }

    fun getCommentDetail(data: String) {
        songId.value = data
    }
}