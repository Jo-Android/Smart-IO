package com.hgtech.smartio.network.model.response.assign.sensor_user

import android.os.Parcelable
import com.hgtech.smartio.network.model.response.sensor.Sensor
import kotlinx.parcelize.Parcelize

@Parcelize
data class SensorUser(
    val id: String,
    val user: UserInfo,
    val sensor: Sensor,
    val isActive: String,
) : Parcelable
