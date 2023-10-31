package com.hgtech.smartio.network.api.sensor

import com.hgtech.smartio.network.model.request.sensor.type.AddSensorTypeRequest
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeListResponse
import com.hgtech.smartio.network.model.response.sensor.type.SensorTypeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SensorTypeApi {
    @GET("getAll.php")
    suspend fun getAllType(): Response<SensorTypeListResponse>

    @POST("insert.php")
    suspend fun insert(@Body type: AddSensorTypeRequest): Response<SensorTypeResponse>
}