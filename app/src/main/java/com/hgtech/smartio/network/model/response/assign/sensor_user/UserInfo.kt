package com.hgtech.smartio.network.model.response.assign.sensor_user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val id: String,
    val userName: String,
    val isManager: String,
    val ipAddress: String
) : Parcelable
