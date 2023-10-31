package com.hgtech.smartio.network.model.response.crop.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CropType(
    val id: String,
    val Category: String
) : Parcelable
