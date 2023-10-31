package com.hgtech.smartio.ui.viewmodel.user

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.model.request.user.LoginRequest
import com.hgtech.smartio.network.model.response.user.login.LoginResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class LoginViewModel(
    private val repository: UserRepository
) : BaseViewModel() {

    val loginResponse = MutableLiveData<LoginResponse>()

    fun login(username: String, password: String) {
        execute {
            repository.login(LoginRequest(username, password))?.let {
                loginResponse.postValue(it)
            }
        }
    }
}