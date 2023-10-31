package com.hgtech.smartio.network.api

import com.hgtech.smartio.network.model.response.irrigation.IrrigationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IrrigationApi {

    @GET("state.php")
    suspend fun get(@Query("userId") userId: String): Response<IrrigationResponse>
}
