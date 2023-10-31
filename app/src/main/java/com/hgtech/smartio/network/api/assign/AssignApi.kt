package com.hgtech.smartio.network.api.assign

import com.hgtech.smartio.network.model.request.assign.SetActiveStateRequest
import com.hgtech.smartio.network.model.request.assign.sensor_crop.AssignSensorCropRequest
import com.hgtech.smartio.network.model.response.assign.sensor_crop.AssignSensorCropInsertResult
import com.hgtech.smartio.network.model.response.assign.state.IsAssignedResponse
import com.hgtech.smartio.network.model.response.assign.state.StateResponse
import com.hgtech.smartio.network.model.response.assign.state.UpdateStateActivationResponse
import retrofit2.Response
import retrofit2.http.*

interface AssignApi {

    @GET("get.php")
    suspend fun getByID(@Query("id") id: String): Response<StateResponse>

    @GET("crop_detail.php")
    suspend fun getCrop(
        @Query("cropId") cropId: String,
        @Query("userId") userId: String
    ): Response<StateResponse>


    @GET("sensor_user.php")
    suspend fun getCrops(
        @Query("sensorId") sensorId: String,
        @Query("userId") userId: String
    ): Response<StateResponse>

    @GET("isAssigned.php")
    suspend fun isAssigned(
        @Query("cropId") cropId: String,
        @Query("sensorType") sensorType: String,
        @Query("userId") userId: String
    ): Response<IsAssignedResponse>

    @POST("insert.php")
    suspend fun insert(@Body request: AssignSensorCropRequest): Response<AssignSensorCropInsertResult>

    @PUT("setActive.php")
    suspend fun setActive(@Body request: SetActiveStateRequest): Response<UpdateStateActivationResponse>
}