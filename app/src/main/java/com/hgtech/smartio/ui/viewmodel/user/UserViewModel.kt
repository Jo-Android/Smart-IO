package com.hgtech.smartio.ui.viewmodel.user

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.manager.UserWrapper
import com.hgtech.smartio.network.model.response.user.BaseResponse
import com.hgtech.smartio.network.model.response.user.get.UserResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserViewModel() : BaseViewModel(), KoinComponent {


    private val repository: UserRepository by inject()
    val userInfo = MutableLiveData<UserResponse>()

    val isFinished = MutableLiveData<BaseResponse>()
    val logout = MutableLiveData<BaseResponse>()

    fun getUserInfo() {
        execute {
            repository.get(UserWrapper.getID())?.let {
                userInfo.postValue(it)
            }
        }
    }

    fun updateUserName(old: String, new: String, password: String) {
        execute {
            repository.updateUsername(UserWrapper.getID(), old, new, password)?.let {
                logout.postValue(it)
            }
        }
    }

    fun updatePassword(email: String, new: String) {
        execute {
            repository.updatePassword(UserWrapper.getID(), new, email)?.let {
                logout.postValue(it)
            }
        }
    }

    fun updatePhone(phone: String) {
        execute {
            repository.updatePhone(UserWrapper.getID(), phone)?.let {
                isFinished.postValue(it)
            }
        }
    }

    fun deleteUser() {
        execute {
            repository.setActive(UserWrapper.getID(), false)?.let {
                logout.postValue(it)
            }
        }
    }

}