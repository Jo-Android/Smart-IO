package com.hgtech.smartio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.user.get.UserResponse
import com.hgtech.smartio.network.model.response.user.get.Users

class SettingsViewModel(
    private val repository: UserRepository
) : BaseViewModel() {

    val user = MutableLiveData<UserResponse>()
    var userInfo: Users? = null

    fun getCredentials() {
        execute {
            repository.get(getID())?.let {
                user.postValue(it)
            }
        }
    }
}