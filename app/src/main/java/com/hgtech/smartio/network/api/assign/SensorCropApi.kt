package com.hgtech.smartio.network.api.assign

import com.hgtech.smartio.network.model.request.assign.sensor_crop.AssignCropSensorTypeRequest
import com.hgtech.smartio.network.model.request.assign.sensor_crop.UpdateValueRequest
import com.hgtech.smartio.network.model.response.UpdateResponse
import com.hgtech.smartio.network.model.response.assign.sensor_crop.InsertCropSensorTypeResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.ListSensorCropResult
import com.hgtech.smartio.network.model.response.assign.sensor_crop.SensorCropResult
import retrofit2.Response
import retrofit2.http.*

interface SensorCropApi {

    @POST("insert.php")
    suspend fun assign(@Body assign: AssignCropSensorTypeRequest): Response<InsertCropSensorTypeResult>

    @GET("sensor_crop.php")
    suspend fun isAssign(
        @Query("sensor") type: String,
        @Query("crop") crop: String
    ): Response<SensorCropResult>

    @GET("sensor_type.php")
    suspend fun getCrops(@Query("sensorId") sensorId: String): Response<ListSensorCropResult>

    @PUT("minValue.php")
    suspend fun updateMinValue(@Body updateValueRequest: UpdateValueRequest): Response<UpdateResponse>

    @PUT("maxValue.php")
    suspend fun updateMaxValue(@Body updateValueRequest: UpdateValueRequest): Response<UpdateResponse>
}