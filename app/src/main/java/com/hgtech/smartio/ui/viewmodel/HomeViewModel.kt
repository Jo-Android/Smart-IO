package com.hgtech.smartio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.irrigation.IrrigationRepository
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.irrigation.IrrigationResponse

class HomeViewModel(
    private val repository: IrrigationRepository
) : BaseViewModel() {

    val response by lazy { MutableLiveData<IrrigationResponse>() }
    fun get() {
        execute {
            repository.get(getID())?.let {
                response.postValue(it)
            }
        }
    }

}