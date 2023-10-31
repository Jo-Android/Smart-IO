package com.hgtech.smartio.network.model.request.user

data class LoginRequest(
    val userName: String,
    val password: String
)