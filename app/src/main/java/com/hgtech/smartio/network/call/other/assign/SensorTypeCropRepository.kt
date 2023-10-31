package com.hgtech.smartio.network.call.other.assign

import com.hgtech.smartio.network.call.base.BaseRepository
import com.hgtech.smartio.network.factory.ApiFactory
import com.hgtech.smartio.network.model.request.assign.sensor_crop.AssignCropSensorTypeRequest
import com.hgtech.smartio.network.model.request.assign.sensor_crop.UpdateValueRequest
import com.hgtech.smartio.network.model.response.UpdateResponse
import com.hgtech.smartio.network.model.response.assign.sensor_crop.InsertCropSensorTypeResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.ListSensorCropResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.SensorCropResult

class SensorTypeCropRepository() : BaseRepository() {

    suspend fun assign(
        sensor: String,
        cropId: String,
        minValue: String,
        maxValue: String
    ): InsertCropSensorTypeResult? {
        return safeApiCall(
            call = {
                ApiFactory.sensorCropApi
                    .assign(AssignCropSensorTypeRequest(cropId, sensor, minValue, maxValue))
            },
            errorMessage = "Something went wrong when trying to get All Crops"
        )
    }

    suspend fun isAssign(type: String, crop: String): SensorCropResult? {
        return safeApiCall(
            call = {
                ApiFactory.sensorCropApi
                    .isAssign(type, crop)
            },
            errorMessage = "Something went wrong when trying to check Assignment "
        )
    }

    suspend fun getCrops(sensorId: String): ListSensorCropResult? {
        return safeApiCall(
            call = {
                ApiFactory.sensorCropApi
                    .getCrops(sensorId)
            },
            errorMessage = "Something went wrong when trying to get Crops "
        )
    }

    suspend fun updateMinValue(cropSensorId: String, value: String): UpdateResponse? {
        return safeApiCall(
            call = {
                ApiFactory.sensorCropApi
                    .updateMinValue(UpdateValueRequest(cropSensorId, value))
            },
            errorMessage = "Something went wrong when trying to update minValue for $cropSensorId "
        )
    }

    suspend fun updateMaxValue(cropSensorId: String, value: String): UpdateResponse? {
        return safeApiCall(
            call = {
                ApiFactory.sensorCropApi
                    .updateMaxValue(UpdateValueRequest(cropSensorId, value))
            },
            errorMessage = "Something went wrong when trying to update minValue for $cropSensorId "
        )
    }

}