package com.hgtech.smartio.network.model.response.assign.state

data class IsAssignedResponse(
    val isSuccess: Boolean,
    val isTypeAssigned: Boolean,
    val message: String
)
