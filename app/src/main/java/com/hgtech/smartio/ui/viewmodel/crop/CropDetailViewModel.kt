package com.hgtech.smartio.ui.viewmodel.crop

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.assign.AssignRepository
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.assign.state.StateResponse
import com.hgtech.smartio.network.model.response.user.get.UserResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class CropDetailViewModel(
    private val stateRepository: AssignRepository
) : BaseViewModel() {

    val userResposne = MutableLiveData<UserResponse>()
    val cropDetail = MutableLiveData<StateResponse>()

    fun getCredentials() {
        execute {
            stateRepository.cropRepository.userRepository.get(getID())?.let {
                userResposne.postValue(it)
            }
        }
    }

    fun getCropDetail(id: String) {
        execute {
            stateRepository.getCrop(id, getID())?.let {
                cropDetail.postValue(it)
            }
        }
    }

    fun updateName(name: String) {

    }

}