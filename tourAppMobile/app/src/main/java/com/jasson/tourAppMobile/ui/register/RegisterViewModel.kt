package com.jasson.tourAppMobile.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jasson.tourAppMobile.model.User
import com.jasson.tourAppMobile.model.repositories.UserRepository

class RegisterViewModel : ViewModel() {

    var logged = MutableLiveData<Boolean>()
    var status = MutableLiveData<String>()
    var userRepository = UserRepository()

    init{
        this.logged = userRepository.logged
        this.status = userRepository.status
    }

    fun registerUser(user: User){
        userRepository.registerUser(user)
    }

}