package com.hgtech.smartio.network.model.response.user.get

data class Users(
    val id: String,
    val managerId: String?,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val phone: String,
    val dob: String,
    val isActive: String,
    val isManager: String,
)
