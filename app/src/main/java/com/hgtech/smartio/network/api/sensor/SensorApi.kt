package com.hgtech.smartio.network.api.sensor

import com.hgtech.smartio.network.model.request.sensor.AddSensorRequest
import com.hgtech.smartio.network.model.response.sensor.AddSensorResponse
import com.hgtech.smartio.network.model.response.sensor.SensorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SensorApi {

    @POST("insert.php")
    suspend fun insert(@Body type: AddSensorRequest): Response<AddSensorResponse>

    @GET("qr.php")
    suspend fun getId(@Query("qr") qr: String): Response<SensorResponse>

    @GET("sensor.php")
    suspend fun get(@Query("id") qr: String): Response<SensorResponse>
}