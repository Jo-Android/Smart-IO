package com.hgtech.smartio.ui.viewmodel.user

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.model.request.user.SignUPRequest
import com.hgtech.smartio.network.model.response.user.signup.SignUPResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class SignUViewModel(
    private val repository: UserRepository
) : BaseViewModel() {
    val signUpResponse = MutableLiveData<SignUPResponse>()

    fun signUP(
        fname: String,
        lname: String,
        date: String,
        phone: String,
        username: String,
        password: String,
    ) {
        execute {
            repository.signUp(
                SignUPRequest(
                    fname,
                    lname,
                    username,
                    password,
                    phone,
                    date,
                    ipAddress = " "
                )
            )?.let {
                signUpResponse.postValue(it)
            }
        }

    }
}