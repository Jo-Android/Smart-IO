package com.hgtech.smartio.network.model.response.assign.state

import android.os.Parcelable
import com.hgtech.smartio.network.model.response.crop.Crop
import kotlinx.parcelize.Parcelize

@Parcelize
data class CropSensor(
    val id: String,
    val crop: Crop,
    val cropCode: String?,
    var minValue: String,
    var maxValue: String,
) : Parcelable
