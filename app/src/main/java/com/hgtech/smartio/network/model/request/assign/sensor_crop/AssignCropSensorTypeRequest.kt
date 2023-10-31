package com.hgtech.smartio.network.model.request.assign.sensor_crop

data class AssignCropSensorTypeRequest(
    val cropId: String,
    val sensorType: String,
    val minValue: String,
    val maxValue: String,
)
