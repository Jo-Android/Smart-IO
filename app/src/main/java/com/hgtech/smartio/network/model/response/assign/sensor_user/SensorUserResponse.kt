package com.hgtech.smartio.network.model.response.assign.sensor_user

data class SensorUserResponse(
    val data: SensorUser?,
    val isSuccess: Boolean,
    val isTypeAssigned: Boolean,
    val message: String
)
