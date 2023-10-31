package com.hgtech.smartio.network.model.response.values

class ValuesResponse(
    val data: List<SensorValues>?,
    val stateId: String,
    val message: String
)