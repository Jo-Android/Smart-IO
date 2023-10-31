package com.hgtech.smartio.network.model.request.user

data class UsernameRequest(
    val id: String,
    val oldUsername: String,
    val newUsername: String,
    val password: String
)