package com.hgtech.smartio.ui.viewmodel.assign.sensor_crop

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.assign.AssignRepository
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.assign.sensor_crop.ListSensorCropResult
import com.hgtech.smartio.network.model.response.assign.state.StateResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class ListCropBySensorViewModel(
    private val repository: AssignRepository
) : BaseViewModel() {

    val stateResponse = MutableLiveData<StateResponse>()
    val cropsBySensorType = MutableLiveData<ListSensorCropResult>()

    fun getCropBySensorType(sensorId: String) {
        execute {
            repository.sensorCrop.getCrops(sensorId)?.let {
                cropsBySensorType.postValue(it)
            }
        }
    }

    fun getStates(sensorId: String) {
        execute {
            repository.getCrops(sensorId, getID())?.let {
                stateResponse.postValue(it)
            }
        }
    }
}