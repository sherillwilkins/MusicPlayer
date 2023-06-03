package com.w83ll43.musicplayer.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.w83ll43.musicplayer.logic.Repository

class LoginViewModel: ViewModel() {

    private val phone = MutableLiveData<String>()

    val phoneLiveData = Transformations.switchMap(phone) {
        Repository.sent(phone.value.toString())
    }

    private val loginData = MutableLiveData<LoginData>()

    val loginDataLiveData = Transformations.switchMap(loginData) {
        Repository.login(loginData.value!!)
    }

    fun sent(data: String) {
        phone.value = data
    }

    fun login(data: LoginData) {
        loginData.value = data
    }
}