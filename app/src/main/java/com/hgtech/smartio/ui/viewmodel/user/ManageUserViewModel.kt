package com.hgtech.smartio.ui.viewmodel.user

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.manager.UserWrapper
import com.hgtech.smartio.network.manager.UserWrapper.get
import com.hgtech.smartio.network.model.response.user.BaseResponse
import com.hgtech.smartio.network.model.response.user.manager.ManagerResponse
import com.hgtech.smartio.network.model.response.user.manager.Users
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class ManageUserViewModel(
    private val repository: UserRepository
) : BaseViewModel() {
    val userList = MutableLiveData<ManagerResponse>()

    val isActive = MutableLiveData<BaseResponse>()
    val isAssign = MutableLiveData<BaseResponse>()

    var position = 0

    fun getUserList() {
        execute {
            repository.getUsers(UserWrapper.getID())?.let {
                userList.postValue(it)
            }
        }
    }

    fun getRegularUsers() {
        execute {
            repository.getRegularUsers()?.let {
                userList.postValue(it)
            }
        }
    }

    fun setActive(user: Users, position: Int, isActive: Boolean) {
        execute {
            repository.setActive(user.id, isActive)?.let {
                if (it.isSuccess) {
                    user.isActive = isActive.get()
                    this.position = position
                }
                this.isActive.postValue(it)
            }
        }
    }

    fun assign(user: Users, position: Int, managerId: String?) {
        execute {
            repository.assign(user.id, managerId)?.let {
                if (it.isSuccess) {
                    this.position = position
                }
                isAssign.postValue(it)
            }
        }
    }

}