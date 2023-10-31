package com.hgtech.smartio.network.model.response.user.get

data class UserResponse(
    val data: List<Users>?,
    val message: String
)