package com.hgtech.smartio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.assign.SensorUserRepository
import com.hgtech.smartio.network.manager.UserWrapper.checkActive
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.assign.sensor_user.AddSensorUserResponse
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUserResponse2
import com.hgtech.smartio.network.model.response.sensor.SensorResponse

class DashboardViewModel(
    private val repository: SensorUserRepository
) : BaseViewModel() {

    val sensorInfo = MutableLiveData<SensorResponse>()
    val isAssign = MutableLiveData<SensorUserResponse2>()
    val assignResponse = MutableLiveData<AddSensorUserResponse>()
    var isManager = false


    fun getSensor(qr: String) {
        execute {
            repository.sensor.getId(qr)?.let {
                sensorInfo.postValue(it)
            }
        }
    }


    fun getSensorById(id: String) {
        execute {
            repository.sensor.get(id)?.let {
                sensorInfo.postValue(it)
            }
        }
    }

    fun isAssign(id: String) {
        execute {
            repository.get(id, getID())?.let {
                isAssign.postValue(it)
            }
        }
    }

    fun assign(id: String) {
        execute {
            repository.assign(getID(), id)?.let {
                assignResponse.postValue(it)
            }
        }
    }

    fun isManager() {
        execute {
            repository.user.get(getID())?.let {
                if (it.data != null && it.data.isNotEmpty())
                    isManager = it.data[0].isManager.checkActive()
            }
        }
    }
}