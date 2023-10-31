package com.hgtech.smartio.ui.viewmodel.sensor

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.sensor.SensorRepository
import com.hgtech.smartio.network.call.other.sensor.SensorTypeRepository
import com.hgtech.smartio.network.model.response.sensor.AddSensorResponse
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeListResponse
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class AddSensorViewModel(
    private val sensorRepository: SensorRepository,
    private val sensorTypeRepository: SensorTypeRepository
) : BaseViewModel() {


    var typeId: String? = null
    val sensorTypeList = MutableLiveData<SensorTypeListResponse>()
    val sensorTypeResponse = MutableLiveData<SensorTypeResponse>()
    val addSensorResponse = MutableLiveData<AddSensorResponse>()

    fun addSensor(name: String, qrcode: String) {
        execute {
            sensorRepository.insert(typeId!!, name, qrcode)?.let {
                addSensorResponse.postValue(it)
            }
        }
    }

    fun getSensorTypeList() {
        execute {
            sensorTypeRepository.getAll()?.let {
                sensorTypeList.postValue(it)
            }
        }
    }

    fun addType(type: String, uof: String) {
        execute {
            sensorTypeRepository.insert(type, uof)?.let {
                sensorTypeResponse.postValue(it)
            }
        }
    }
}