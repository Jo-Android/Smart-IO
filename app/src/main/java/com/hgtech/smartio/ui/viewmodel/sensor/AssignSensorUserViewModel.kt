package com.hgtech.smartio.ui.viewmodel.sensor

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.assign.SensorUserRepository
import com.hgtech.smartio.network.call.other.sensor.SensorRepository
import com.hgtech.smartio.network.model.response.assign.sensor_user.AddSensorUserResponse
import com.hgtech.smartio.network.model.response.sensor.SensorResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class AssignSensorUserViewModel(
    private val sensorRepository: SensorRepository,
    private val sensorUserRepository: SensorUserRepository
) : BaseViewModel() {

    val sensor = MutableLiveData<SensorResponse>()
    val assignResponse = MutableLiveData<AddSensorUserResponse>()
    fun assign(userID: String, sensorID: String) {
        execute {
            sensorUserRepository.assign(userID, sensorID)?.let {
                assignResponse.postValue(it)
            }
        }
    }

    fun getSensorByQr(qr: String) {
        execute {
            sensorRepository.getId(qr)?.let {
                sensor.postValue(it)
            }
        }
    }

    fun updateUof(uof: String) {

    }


    fun updateSensorName(name: String) {

    }

}