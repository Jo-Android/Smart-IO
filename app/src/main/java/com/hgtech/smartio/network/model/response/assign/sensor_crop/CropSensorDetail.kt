package com.hgtech.smartio.network.model.response.assign.sensor_crop

import com.hgtech.smartio.network.model.response.crop.Crop
import com.hgtech.smartio.network.model.response.sensor.Sensor

data class CropSensorDetail(
    val id: String,
    val crop: Crop,
    val sensor: Sensor,
    val minValue: String,
    val maxValue: String,
)
