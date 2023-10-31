package com.hgtech.smartio.network.model.response.user.manager

data class Users(
    val id: String,
    var managerID: String?,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val phone: String,
    val dob: String,
    var isActive: String
)
