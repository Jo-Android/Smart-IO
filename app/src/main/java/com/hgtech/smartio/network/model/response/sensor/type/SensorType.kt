package com.hgtech.smartio.network.model.response.sensor.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SensorType(
    val id: String,
    val type: String,
    val uof: String
) : Parcelable
