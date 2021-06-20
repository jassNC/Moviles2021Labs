package com.jasson.tourAppMobile.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jasson.tourAppMobile.model.User
import com.jasson.tourAppMobile.model.repositories.UserRepository

class ProfileViewModel : ViewModel() {

    var user = MutableLiveData<User>()
    var status = MutableLiveData<String>()
    var userRepository = UserRepository();

    init{
        this.user = userRepository.user
        this.status = userRepository.status
    }

    fun loginUser(p_user: User){
        userRepository.userLogin(p_user)
    }

    fun logout(){
        this.user.value = User()
    }

}