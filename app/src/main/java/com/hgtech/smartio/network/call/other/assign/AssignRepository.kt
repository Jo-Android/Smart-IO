package com.hgtech.smartio.network.call.other.assign

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.call.other.crop.CropsRepository
import com.hgtech.smartio.network.call.other.sensor.SensorRepository
import com.hgtech.smartio.network.factory.ApiFactory
import com.hgtech.smartio.network.model.request.assign.SetActiveStateRequest
import com.hgtech.smartio.network.model.request.assign.sensor_crop.AssignSensorCropRequest
import com.hgtech.smartio.network.model.response.assign.sensor_crop.AssignSensorCropInsertResult
import com.hgtech.smartio.network.model.response.assign.state.IsAssignedResponse
import com.hgtech.smartio.network.model.response.assign.state.StateResponse
import com.hgtech.smartio.network.model.response.assign.state.UpdateStateActivationResponse

class AssignRepository : BaseRepository() {

    val cropRepository by lazy {
        CropsRepository()
    }

    val sensorRepository by lazy {
        SensorRepository()
    }

    val sensorCrop by lazy {
        SensorTypeCropRepository()
    }

    val sensorUser by lazy {
        SensorUserRepository()
    }

    suspend fun get(id: String): StateResponse? {
        return safeApiCall(
            call = { ApiFactory.assignApi.getByID(id) },
            errorMessage = "Something went wrong when trying to get State: $id"
        )
    }

    suspend fun getCrop(cropId: String, userId: String): StateResponse? {
        return safeApiCall(
            call = { ApiFactory.assignApi.getCrop(cropId, userId) },
            errorMessage = "Something went wrong when trying to get Crop State: $cropId"
        )
    }

    suspend fun insert(
        sensorUser: String,
        sensorCrop: String,
        startDate: String
    ): AssignSensorCropInsertResult? {
        return safeApiCall(
            call = {
                ApiFactory.assignApi
                    .insert(AssignSensorCropRequest(sensorUser, sensorCrop, startDate))
            },
            errorMessage = "Something went wrong when trying to Assing Sensor Crop :"
        )
    }

    suspend fun getCrops(sensorId: String, userId: String): StateResponse? {
        return safeApiCall(
            call = { ApiFactory.assignApi.getCrops(sensorId, userId) },
            errorMessage = "Something went wrong when trying to get Crop State"
        )
    }

    suspend fun changeActiveState(
        id: String,
        isActive: Boolean,
        date: String
    ): UpdateStateActivationResponse? {
        return safeApiCall(
            call = {
                ApiFactory.assignApi.setActive(SetActiveStateRequest(id, date, isActive))
            },
            errorMessage = "Something went wrong when trying to change activation of State"
        )
    }

    suspend fun isCropSensorTypeAssigned(
        cropId: String,
        sensorType: String,
        userId: String
    ): IsAssignedResponse? {
        return safeApiCall(
            call = {
                ApiFactory.assignApi.isAssigned(cropId, sensorType, userId)
            },
            errorMessage = "Something went wrong when trying to change activation of State"
        )
    }
}