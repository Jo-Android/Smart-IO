package com.hgtech.smartio.network.call.other.sensor

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory.sensorApi
import com.hgtech.smartio.network.model.request.sensor.AddSensorRequest
import com.hgtech.smartio.network.model.response.sensor.AddSensorResponse
import com.hgtech.smartio.network.model.response.sensor.SensorResponse

class SensorRepository : BaseRepository() {

    val typeRepository by lazy {
        SensorTypeRepository()
    }

    suspend fun insert(typeId: String, name: String, qr: String): AddSensorResponse? {
        return safeApiCall(
            call = { sensorApi.insert(AddSensorRequest(typeId, name, qr)) },
            errorMessage = "Something went wrong when trying to Add Sensor : $name"
        )
    }

    suspend fun getId(qr: String): SensorResponse? {
        return safeApiCall(
            call = { sensorApi.getId(qr) },
            errorMessage = "Something went wrong when trying to get Sensor : $qr"
        )
    }

    suspend fun get(id: String): SensorResponse? {
        return safeApiCall(
            call = { sensorApi.get(id) },
            errorMessage = "Something went wrong when trying to get Sensor : $id"
        )
    }

    fun updateUof(uof: String) {

    }

    fun updateName(it: String) {

    }
}