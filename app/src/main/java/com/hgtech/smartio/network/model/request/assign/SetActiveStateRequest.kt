package com.hgtech.smartio.network.model.request.assign

data class SetActiveStateRequest(
    val id: String,
    val changeDate: String,
    val isActive: Boolean,
)
