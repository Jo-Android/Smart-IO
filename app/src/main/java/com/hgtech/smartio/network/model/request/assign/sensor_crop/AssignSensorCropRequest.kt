package com.hgtech.smartio.network.model.request.assign.sensor_crop

data class AssignSensorCropRequest(
    val sensorUser: String,
    val sensorCrop: String,
    val startDate: String
)
