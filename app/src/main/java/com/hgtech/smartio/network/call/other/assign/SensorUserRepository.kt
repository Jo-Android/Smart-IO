package com.hgtech.smartio.network.call.other.assign

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.call.other.sensor.SensorRepository
import com.hgtech.smartio.network.call.other.user.UserRepository
import com.hgtech.smartio.network.factory.ApiFactory.sensorUserApi
import com.hgtech.smartio.network.model.request.assign.sensor_user.AddSensorUserRequest
import com.hgtech.smartio.network.model.response.assign.sensor_user.AddSensorUserResponse
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUserResponse2

class SensorUserRepository : BaseRepository() {

    val sensor: SensorRepository by lazy {
        SensorRepository()
    }

    val user: UserRepository by lazy {
        UserRepository()
    }

    suspend fun assign(userId: String, sensorId: String): AddSensorUserResponse? {
        return safeApiCall(
            call = { sensorUserApi.insert(AddSensorUserRequest(userId, sensorId)) },
            errorMessage = "Something went wrong when trying to Add SensorUser : $userId - $sensorId"
        )
    }

    suspend fun get(sensorId: String, userId: String): SensorUserResponse2? {
        return safeApiCall(
            call = { sensorUserApi.get(userId, sensorId) },
            errorMessage = "Something went wrong when trying to Check SensorUser : $userId - $sensorId"
        )
    }
}