package com.hgtech.smartio.network.model.request.user

data class SignUPRequest(
    val fname: String,
    val lname: String,
    val username: String,
    val password: String,
    val phone: String,
    val dob: String,
    val isManager: Int = 0,
    val ipAddress: String
)
