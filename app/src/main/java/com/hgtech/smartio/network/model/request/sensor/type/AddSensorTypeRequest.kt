package com.hgtech.smartio.network.model.request.sensor.type

data class AddSensorTypeRequest(
    val type: String,
    val uof: String
)
