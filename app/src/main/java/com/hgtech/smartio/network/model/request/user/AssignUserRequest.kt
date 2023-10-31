package com.hgtech.smartio.network.model.request.user

data class AssignUserRequest(
    val id: String,
    val managerID: String?
)
