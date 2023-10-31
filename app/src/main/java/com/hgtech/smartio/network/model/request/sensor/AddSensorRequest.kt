package com.hgtech.smartio.network.model.request.sensor

data class AddSensorRequest(
    val type: String,
    val name: String,
    val qr: String
)
