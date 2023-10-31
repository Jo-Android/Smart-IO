package com.hgtech.smartio.network.model.response.sensor

import android.os.Parcelable
import com.hgtech.smartio.network.model.response.sensor.type.SensorType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sensor(
    val id: String,
    val type: SensorType,
    val name: String,
    val qr: String
) : Parcelable
