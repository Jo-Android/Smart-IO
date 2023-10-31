package com.hgtech.smartio.network.api.assign

import com.hgtech.smartio.network.model.request.assign.sensor_user.AddSensorUserRequest
import com.hgtech.smartio.network.model.response.assign.sensor_user.AddSensorUserResponse
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUserResponse
import com.hgtech.smartio.network.model.response.assign.sensor_user.SensorUserResponse2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SensorUserApi {

    @POST("insert.php")
    suspend fun insert(@Body assign: AddSensorUserRequest): Response<AddSensorUserResponse>

    @GET("sensor_user.php")
    suspend fun get(
        @Query("user") userId: String,
        @Query("sensor") sensorId: String
    ): Response<SensorUserResponse2>

    @GET("isAssigned.php")
    suspend fun isAssign(
        @Query("user") userId: String,
        @Query("sensor") sensorId: String
    ): Response<SensorUserResponse>
}