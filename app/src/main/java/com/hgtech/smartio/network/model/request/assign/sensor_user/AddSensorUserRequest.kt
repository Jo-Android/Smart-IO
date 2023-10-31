package com.hgtech.smartio.network.model.request.assign.sensor_user

data class AddSensorUserRequest(
    val userId: String,
    val sensorId: String
)
