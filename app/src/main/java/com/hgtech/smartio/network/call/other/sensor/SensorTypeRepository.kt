package com.hgtech.smartio.network.call.other.sensor

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory.sensorTypeApi
import com.hgtech.smartio.network.model.request.sensor.type.AddSensorTypeRequest
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeListResponse
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeResponse

class SensorTypeRepository : BaseRepository() {

    suspend fun getAll(): SensorTypeListResponse? {
        return safeApiCall(
            call = { sensorTypeApi.getAllType() },
            errorMessage = "Something went wrong when trying to Get All Sensor type: "
        )
    }

    suspend fun insert(type: String, uof: String): SensorTypeResponse? {
        return safeApiCall(
            call = { sensorTypeApi.insert(AddSensorTypeRequest(type, uof)) },
            errorMessage = "Something went wrong when trying to Add Sensor type: $type"
        )
    }

    fun updateName(it: String) {

    }

}