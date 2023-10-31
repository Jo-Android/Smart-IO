package com.hgtech.smartio.network.model.response.crop

import android.os.Parcelable
import com.hgtech.smartio.network.model.response.crop.type.CropType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crop(
    val id: String,
    val category: CropType,
    val name: String,
) : Parcelable