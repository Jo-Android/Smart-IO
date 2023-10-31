package com.hgtech.smartio.ui.viewmodel.assign.sensor_crop

import androidx.lifecycle.MutableLiveData
import com.hgtech.smartio.network.call.other.assign.AssignRepository
import com.hgtech.smartio.network.call.other.values.ValuesRepository
import com.hgtech.smartio.network.manager.UserWrapper.getDate
import com.hgtech.smartio.network.manager.UserWrapper.getID
import com.hgtech.smartio.network.model.response.UpdateResponse
import com.hgtech.smartio.network.model.response.assign.sensor_crop.AssignSensorCropInsertResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.InsertCropSensorTypeResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.SensorCropResult
import com.hgtech.smartio.network.model.response.assign.sensor_user.AddSensorUserResponse
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUserResponse2
import com.hgtech.smartio.network.model.response.assign.state.IsAssignedResponse
import com.hgtech.smartio.network.model.response.assign.state.StateResponse
import com.hgtech.smartio.network.model.response.assign.state.UpdateStateActivationResponse
import com.hgtech.smartio.network.model.response.crop.AllCropsResponse
import com.hgtech.smartio.network.model.response.crop.Crop
import com.hgtech.smartio.network.model.response.sensor.SensorResponse
import com.hgtech.smartio.network.model.response.values.ValuesResponse
import com.hgtech.smartio.ui.viewmodel.BaseViewModel

class AssignCropSensorViewModel(
    private val repository: AssignRepository,
    private val valuesRepository: ValuesRepository
) : BaseViewModel() {

    var isStateActive = false

    var crop: Crop? = null
    val allCrops by lazy { MutableLiveData<AllCropsResponse>() }
    val sensor by lazy { MutableLiveData<SensorResponse>() }
    val stateResponse by lazy { MutableLiveData<StateResponse>() }
    val valuesResponse by lazy { MutableLiveData<ValuesResponse>() }

    val assignSensorUser by lazy { MutableLiveData<AddSensorUserResponse>() }
    val assignSensorTypeCrop by lazy { MutableLiveData<InsertCropSensorTypeResult>() }
    val assign by lazy { MutableLiveData<AssignSensorCropInsertResult>() }

    val isSensorTypeCropAssign by lazy { MutableLiveData<SensorCropResult>() }
    val isSensorUserAssign by lazy { MutableLiveData<SensorUserResponse2>() }
    val isCropSensorTypeAssign by lazy { MutableLiveData<IsAssignedResponse>() }

    val updateState by lazy { MutableLiveData<UpdateStateActivationResponse>() }

    val updateValue by lazy { MutableLiveData<UpdateResponse>() }

    fun getState(id: String) {
        execute {
            repository.get(id)?.let {
                stateResponse.postValue(it)
            }
        }
    }

    fun getAllCrops() {
        execute {
            repository.cropRepository.getAll()?.let {
                allCrops.postValue(it)
            }
        }
    }

    fun updateSensorName(sensorName: String) {

    }

    fun updateUof(uof: String) {

    }

    fun getSensorByQr(qr: String) {
        execute {
            repository.sensorRepository.getId(qr)?.let {
                sensor.postValue(it)
            }
        }
    }

    fun getSensor(id: String) {
        execute {
            repository.sensorRepository.get(id)?.let {
                sensor.postValue(it)
            }
        }
    }

    fun isSensorTypeCopAssigned(type: String, crop: String) {
        execute {
            repository.sensorCrop.isAssign(type, crop)?.let {
                isSensorTypeCropAssign.postValue(it)
            }
        }
    }

    fun isSensorUserAssigned(sensor: String) {
        execute {
            repository.sensorUser.get(sensor, getID())?.let {
                isSensorUserAssign.postValue(it)
            }
        }
    }

    fun assignCropSensorType(cropId: String, sensor: String, minValue: String, maxValue: String) {
        execute {
            repository.sensorCrop.assign(sensor, cropId, minValue, maxValue)?.let {
                assignSensorTypeCrop.postValue(it)
            }
        }
    }

    fun assignSensorUser(sensor: String) {
        execute {
            repository.sensorUser.assign(getID(), sensor)?.let {
                assignSensorUser.postValue(it)
            }
        }
    }

    fun isCropSensorTypeAssigned(cropId: String, sensorType: String) {
        execute {
            repository.isCropSensorTypeAssigned(cropId, sensorType, getID())?.let {
                isCropSensorTypeAssign.postValue(it)
            }
        }
    }

    fun assignState(cropSensor: String, sensorUser: String) {
        execute {
            repository.insert(sensorUser, cropSensor, getDate())?.let {
                assign.postValue(it)
            }
        }
    }

    fun updateCropName(cropName: String) {

    }

    fun updateMinValue(value: String, cropSensorId: String) {
        execute {
            repository.sensorCrop.updateMinValue(cropSensorId, value)?.let {
                updateValue.postValue(it)
            }
        }
    }

    fun updateMaxValue(value: String, cropSensorId: String) {
        execute {
            repository.sensorCrop.updateMaxValue(cropSensorId, value)?.let {
                updateValue.postValue(it)
            }
        }
    }

    fun changeActiveState(assignId: String) {
        isStateActive = !isStateActive
        execute {
            repository.changeActiveState(assignId, isStateActive, getDate())?.let {
                updateState.postValue(it)
            }
        }
    }

    fun getValues(id: String) {
        execute {
            valuesRepository.get(id)?.let {
                valuesResponse.postValue(it)
            }
        }
    }
}