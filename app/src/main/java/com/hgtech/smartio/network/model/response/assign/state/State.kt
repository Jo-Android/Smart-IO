package com.hgtech.smartio.network.model.response.assign.state

import android.os.Parcelable
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(
    val id: String,
    val sensor_crop: CropSensor,
    val sensor_user: SensorUser,
    val startDate: String,
    val endDate: String?,
    val isActive: String
):Parcelable