package com.hgtech.smartio.network.model.response.irrigation

data class IrrigationResponse(
    val data: List<Irrigation>?,
    val message: String
)
