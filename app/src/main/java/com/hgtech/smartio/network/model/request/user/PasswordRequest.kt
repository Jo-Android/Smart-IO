package com.hgtech.smartio.network.model.request.user

data class PasswordRequest(
    val id: String,
    val userName: String,
    val newPassword: String
)
